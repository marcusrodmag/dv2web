package inf.marcus.dv2web.utils.business.flow;

import inf.marcus.dv2web.utils.business.encoder.EncodingRequester;
import inf.marcus.dv2web.utils.business.encoder.GetStatus;

import java.io.IOException;

public class MonitoringMediaTransfer {
	private int mediaID;
	
	public MonitoringMediaTransfer(int mediaID){
		this.mediaID = mediaID;
	}

	public void monitor(){
		int errorCount = 0;
		int maxErrorAccepted = 10;
		boolean videoPrepared = false;
		System.out.println("Aguardando trasnfer�nciado arquivo.");
		while (!videoPrepared) {
			GetStatus getStatus = new GetStatus(mediaID);
			String statusResponse = "";
			try {
				getStatus.execute();
			} catch (IOException e1) {
				errorCount++;
				System.err.println("Erro #" + errorCount + " ao requisitar status da transfer�ncia do arquivo de v�deo\n" + e1.getMessage());
			}
			if(!getStatus.isValidResponse()){
				throw new RuntimeException("N�o foi poss�vel recuperar o status da a convers�o.");
			}
			String status = getStatus.getStatus();
			if(status.equals("Ready to process")){
				videoPrepared = true;
				System.out.println("Video dispon�vel para convers�o.");
			}
			if(status.equals("Error")){
				throw new RuntimeException("N�o foi poss�vel tranferir o v�deo para o servi�o de codifica��o.");
			}
			if(errorCount == maxErrorAccepted){
				throw new RuntimeException("N�o foi poss�vel obter o status da transfer�ncia do v�deo.");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
		}
		
	}

}
