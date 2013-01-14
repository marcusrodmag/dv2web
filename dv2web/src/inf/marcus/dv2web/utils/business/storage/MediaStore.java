package inf.marcus.dv2web.utils.business.storage;

import inf.marcus.dv2web.utils.constants.ConstantsAWS;
import inf.marcus.dv2web.utils.exceptions.VideoConversionException;
import inf.marcus.dv2web.web.business.DV2WEBFileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.springframework.util.FileCopyUtils;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.iterable.S3Objects;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * Envia o arquivo de mídia para o servidor de armazenamento.
 * @author marcus
 */
public class MediaStore {
	
	private File fileToUpload;
	AmazonS3Client s3Client;
	PropertiesCredentials s3Credentials;
	/**
	 * Envia o arquivo identificado pelo Stream e com seu respectivo nome.
	 * @param fileToUpload Arquivo a ser enviado.
	 */
	public MediaStore(File fileToUpload){
		this.fileToUpload = fileToUpload;
	}
	
	/**
	 * Permite a utilização dos metodos internos em métodos estáticos.
	 */
	private MediaStore(){

	}
	
	public void execute() throws VideoConversionException{
		this.doConnect();
		if(this.fileAlreadyExists()){
			System.err.printf("O Arquivo %s já encontra-se no servidor.", this.fileToUpload.getName());
			throw new VideoConversionException("Já existe um arquivo com este nome no servidor de armazenamento");
		}
		this.upload();
		this.setACLReadPublic();
	}
	/**
	 * Retorna a lista de arquivos ja convertidos em um mapa contendo o nome do arquivo e sua URL.
	 * @return
	 */
	public static TreeMap<String,String> getConvertedFiles(){
		MediaStore media = new MediaStore();
		TreeMap<String,String> fileList = new TreeMap<String,String>();
		try {
			media.doConnect();
		} catch (VideoConversionException e) {
			throw new RuntimeException("Erro ao conectado com servidor de armazenamento." + e.getMessage());
		}
		for ( S3ObjectSummary summary : S3Objects.withPrefix(media.s3Client, ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ENCODED_SUBDIR) ) {
		    if(!summary.getKey().equals(ConstantsAWS.AWS_S3_BUCKET_ENCODED_SUBDIR + "/") && summary.getKey().endsWith(DV2WEBFileUtils.CONVERTED_FILE_EXTENCION)){
		    	System.out.printf("Arquivo convertido listado: '%s'\n", summary.getKey());
		    	fileList.put(summary.getKey().replaceFirst(ConstantsAWS.AWS_S3_BUCKET_ENCODED_SUBDIR + "/", ""),ConstantsAWS.AWS_S3_PUBLIC_URL + "/" + ConstantsAWS.AWS_S3_BUCKET_NAME + "/" + ConstantsAWS.AWS_S3_BUCKET_ENCODED_SUBDIR + "/" + summary.getKey());
		    }
		}
		return fileList;
	}
	
	private void doConnect() throws VideoConversionException{
		try {
			s3Credentials = new PropertiesCredentials(MediaStore.class.getResourceAsStream("AwsCredentials.properties"));
		} catch (IOException e) {
			throw new VideoConversionException("Não foi possível obter as informações de conexão com a AWS");
		}
		s3Client = new AmazonS3Client(s3Credentials);
		s3Client.setEndpoint(ConstantsAWS.AWS_S3_URL);
	}
	
	private boolean fileAlreadyExists(){
		for ( S3ObjectSummary summary : S3Objects.withPrefix(this.s3Client, ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ORIGINAL_SUBDIR) ) {
		    System.out.printf("Arquivo encontrado: '%s'\n", summary.getKey());
		    if(summary.getKey().contains(this.fileToUpload.getName())){
		    	return true;
		    }
		}
		return false;
	}
	
	private void upload() throws VideoConversionException{
		System.out.print("Enviando arquivo para o storage: " + this.fileToUpload.getName());
		s3Client.putObject(ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ORIGINAL_SUBDIR + "/" + fileToUpload.getName(), this.fileToUpload);
		System.out.println(" [Sucesso]");
	}
	
	/**
	 * Tornar o arquivo público para que encoding.com consiga acessá-lo.
	 */
	private void setACLReadPublic(){
		System.out.print("Ajustando permissões de leitura para encoding.com");
		PutObjectRequest changeACLRequest = new PutObjectRequest(ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ORIGINAL_SUBDIR + "/" + fileToUpload.getName(), this.fileToUpload);
		changeACLRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		s3Client.putObject(changeACLRequest);
		System.out.println(" [OK]");
	}
	/**
	 * Apenas para testes.
	 * @param args
	 */
	public static void main(String[] args) {
		String testeFileName = "dogsample.wmv";
		String testPatternDir = "E:";
		// Evitar que a validação aponte existência do arquivo de testes.
		File testFile = new File(testPatternDir, testeFileName);
		File tempFile = new File(testPatternDir, new Date().getTime() + ".wmv");
		try {
			FileCopyUtils.copy(testFile, tempFile);
		} catch (IOException e2) {
			e2.printStackTrace();
			System.exit(2);
		}
		try {
			new MediaStore(tempFile).execute();
		} catch (VideoConversionException e) {
			e.printStackTrace();
			System.exit(1);
		}
		//Limpando arquivo de testes.
		FileUtils.deleteQuietly(tempFile);
	}
	/**
	 * Torna o vídeo convertido público para permitir publicação na internet.
	 */
	public void setACLVideoConverted() {
		System.out.print("Ajustando permissões de leitura para realizar a publicação na internet.");
		PutObjectRequest changeACLRequest = new PutObjectRequest(ConstantsAWS.AWS_S3_BUCKET_NAME, ConstantsAWS.AWS_S3_BUCKET_ENCODED_SUBDIR + "/" + fileToUpload.getName(), this.fileToUpload);
		changeACLRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		s3Client.putObject(changeACLRequest);
		System.out.println(" [OK]");
	}
}
