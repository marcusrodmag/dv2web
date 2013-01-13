package inf.marcus.dv2web.utils.business.flow;

import inf.marcus.dv2web.utils.business.encoder.GetStatus;
import inf.marcus.dv2web.utils.exceptions.EncodingConversionException;

public class MonitoringMediaTransferFlow {
	private int mediaID;
	
	public MonitoringMediaTransferFlow(int mediaID){
		this.mediaID = mediaID;
	}

	public void monitor() throws EncodingConversionException{
		int errorCount = 0;
		int maxErrorAccepted = 10;
		boolean videoPrepared = false;
		System.out.println("Aguardando trasnfer�nciado arquivo.");
		while (!videoPrepared) {
			if(errorCount == maxErrorAccepted){
				throw new EncodingConversionException("N�mero m�ximo de tentativas para realizar a requisi��o ao servi�o.");
			}
			GetStatus getStatus = new GetStatus(mediaID);
			try {
				getStatus.execute();
			} catch (EncodingConversionException e) {
				errorCount++;
				System.err.println("Erro #" + errorCount + ". Falhar ao requisitar status da transfer�ncia do arquivo de v�deo\n" + e.getMessage());
				continue;
			}
			if(!getStatus.isValidResponse()){
				errorCount++;
				System.err.println("Erro #" + errorCount + ". Retorno de status inv�lido\n");
				continue;
			}
			String status = getStatus.getStatus();
			if(status.equals("Ready to process")){
				videoPrepared = true;
				System.out.println("Video dispon�vel para convers�o.");
			}
			if(status.equals("Error")){
				throw new EncodingConversionException("N�o foi poss�vel tranferir o v�deo para o servi�o de codifica��o.");
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
		}
		
	}

}
