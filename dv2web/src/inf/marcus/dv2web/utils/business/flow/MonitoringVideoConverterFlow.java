package inf.marcus.dv2web.utils.business.flow;

import inf.marcus.dv2web.utils.business.encoder.GetStatus;
import inf.marcus.dv2web.utils.exceptions.EncodingConversionException;

public class MonitoringVideoConverterFlow {
	private int mediaID;
	public MonitoringVideoConverterFlow(int mediaID){
		this.mediaID = mediaID;
	}
	
	public void monitor() throws EncodingConversionException{
		int errorCount = 0;
		int maxErrorAccepted = 10;
		boolean videoConverted = false;
		System.out.println("Aguardando processamento arquivo #" + this.mediaID);
		while (!videoConverted) {
			GetStatus getStatus = new GetStatus(mediaID);
			try {
				getStatus.execute();
			} catch (EncodingConversionException e1) {
				errorCount++;
				System.err.println("Erro #" + errorCount + " ao requisitar status da conversão do arquivo de vídeo\n" + e1.getMessage());
				continue;
			}
			if(!getStatus.isValidResponse()){
				errorCount++;
				System.err.println("Não foi possível recuperar o status da a conversão. #" + this.mediaID);
				continue;
			}
			String status = getStatus.getStatus();
			if(status.equals("Finished")){
				videoConverted = true;
				System.out.println("Vídeo convertido.");
			}
			if(status.equals("Error")){
				throw new EncodingConversionException("Não foi possível converter o vídeo.");
			}
			if(errorCount == maxErrorAccepted){
				throw new EncodingConversionException("Número de erros limite exedido ao tentar obter informações sobre conversão de arquivo de média. \nVerifique conexão com o servido.");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
	}

}
