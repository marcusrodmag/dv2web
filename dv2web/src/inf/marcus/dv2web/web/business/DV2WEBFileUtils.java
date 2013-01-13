package inf.marcus.dv2web.web.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DV2WEBFileUtils {
	private File localVideoFile;
	/** Diret�rio tempor�rio para receber o arquivo de v�deo e depois envia-lo para o Storage. */
	private static final String TEMP_APPSERVER_REPOSITORY_DIR = System.getProperty("java.io.tmpdir");
	
	public DV2WEBFileUtils(String sendFileName){
		this.localVideoFile = new File(DV2WEBFileUtils.TEMP_APPSERVER_REPOSITORY_DIR, sendFileName);
	}

	/**
	 * Grava o arquivo enviado temporariamente no servido da aplica��o.
	 * @param input InputStream do arquivo enviado.
	 * @param filename Nome do arquivo que ser� gravado.
	 */
	public void storeTemporaryFile(InputStream input) throws IOException {
		int reader = 0;
		byte[] bytes = new byte[1024];
		System.out.println("Criando arquivo tempor�rio de v�deo: " + localVideoFile.getAbsolutePath());
		OutputStream output = null;
		try {
			output = new FileOutputStream(localVideoFile);
			while((reader = input.read(bytes)) != -1){
				output.write(bytes, 0, reader);
			}
			input.close();
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("Destino do arquivo n�o est� correto: " + localVideoFile.getAbsolutePath());
			throw new RuntimeException("O local de grava��o n�o foi encontrador: " + e.getMessage());
		}
	}
	/** Obtem o arquivo tempor�rio usando no processo de envio de e-mail. 
	 * @return Arquivo tempor�rio.
	 */
	public File getLocalFile() {
		return this.localVideoFile;
	}
	/**
	 * Apaga todos os arquivos tempor�rios usados pelo processo.
	 */
	public void deleteLocalFile(){
		System.out.println("Excluindo arquivo de v�deo tempor�rio");
		this.localVideoFile.delete();
	}

}
