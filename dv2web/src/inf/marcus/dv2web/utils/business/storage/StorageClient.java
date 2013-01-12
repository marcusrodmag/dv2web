package inf.marcus.dv2web.utils.business.storage;

import inf.marcus.dv2web.utils.constants.ConstantsAWS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.iterable.S3Objects;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Envia o arquivo de mídia para o servidor de armazenamento.
 * @author marcus
 */
public class StorageClient {
	
	private String fileName;
	AmazonS3Client s3Client;
	PropertiesCredentials s3Credentials;
	public StorageClient(String fileToUpload){
		this.fileName = fileToUpload;
	}
	
	private void doConnect() throws IOException{
		s3Credentials = new PropertiesCredentials(StorageClient.class.getResourceAsStream("AwsCredentials.properties"));
		s3Client = new AmazonS3Client(s3Credentials);
		s3Client.setEndpoint(ConstantsAWS.AWS_S3_URL);
	}
	
	private boolean fileAlreadyExists(){
		for ( S3ObjectSummary summary : S3Objects.withPrefix(this.s3Client, ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ORIGINAL_SUBDIR) ) {
		    System.out.printf("Object with key '%s'\n", summary.getKey());
		    if(summary.getKey().contains(this.fileName)){
		    	return true;
		    }
		}
		return false;
	}
	
	public void upload() throws IOException{
		File uploadFile = new File(this.fileName);
		this.doConnect();
		if(this.fileAlreadyExists()){
			System.err.printf("O Arquivo %s já encontra-se no servidor.", this.fileName);
			throw new RuntimeException("Já existe um arquivo com este nome no servidor de armazenamento");
		}
		InputStream input = new FileInputStream(uploadFile);
		InputStream inputMetadata = new FileInputStream(uploadFile);
		
		ObjectMetadata metadata = new ObjectMetadata();
		
		System.out.println("Iniciando envio do arquivo: " + fileName);
		byte[] contentBytes = IOUtils.toByteArray(inputMetadata);
		Long contentLength = Long.valueOf(contentBytes.length);
		metadata.setContentLength(contentLength);
		
		PutObjectResult result = s3Client.putObject(ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ORIGINAL_SUBDIR + "/" + uploadFile.getName(), input, metadata);
//		PutObjectResult result = s3Client.putObject(ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ORIGINAL_SUBDIR + "/" + uploadFile.getName(), uploadFile);
		System.out.println("Resultado do upload: " + result.toString());
	}
	
	public static void main(String[] args) {
		StorageClient client = new StorageClient("E:/6795605366_4a21b8535f.jpg");
		try {
			client.upload();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Arquivo enviado para o servidor de armazenamento.");
	}
}
