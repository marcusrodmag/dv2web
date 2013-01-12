package inf.marcus.dv2web.web.business;

import java.io.File;

public class VideoUtils {
	
	
	public boolean convertVideoFile(String fileName){
		File originalVideoFile = new File(FileUtils.ORIGINAL_FILE_DIR, fileName);
		File convertedVideoFile = new File(FileUtils.RECODED_FILE_DIR, fileName);

		if(!originalVideoFile.exists()){
			System.err.println("Arquivo original não encontrado");
			return false;
		}
		
		if(convertedVideoFile.exists()){
			System.err.println("Arquivo já foi convertido");
			return false;
		}
		
		/**
		 * @TODO: Chamar WS para conversão e gravar arquivo de saida
		 */
		return true;
	}
	
	public void streamVideo(String fileName){
		/* @TODO: apresentar o video. */
	}

}
