package inf.marcus.dv2web.jsp.business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

	public static final String ORIGINAL_FILE_DIR = "../dv2webfiles/original";
	public static final String RECODED_FILE_DIR = "../dv2webfiles/recoded";

	/**
	 * Responsável por gravar o arquivo original enviado pelo post na sua devida pasta.
	 * @param input InputStream do arquivo enviado.
	 * @param filename Nome do arquivo que será gravado.
	 * @return true se o arquivo foi gravado corretamente na pasta de destino.
	 */
	public boolean writeOriginalFile(InputStream input, String filename) {
		int reader = 0;
		byte[] bytes = new byte[1024];
		File videoFile = new File(FileUtils.ORIGINAL_FILE_DIR, filename);
		OutputStream output = null;
		try {
			output = new FileOutputStream(videoFile);
			while((reader = input.read(bytes)) != -1){
				output.write(bytes, 0, reader);
			}
			input.close();
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			System.err.println("Arquivo não encontrado: " + e.getMessage());
			return false;
		} catch (IOException e) {
			System.err.println("Erro oo gravar arquivo original no servidor da aplicação: " + e.getMessage());
			return false;
		}
		return true;
	}

}
