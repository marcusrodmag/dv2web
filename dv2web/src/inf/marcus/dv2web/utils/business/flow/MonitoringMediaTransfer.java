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
		System.out.println("Aguardando trasnferênciado arquivo.");
		while (!videoPrepared) {
			GetStatus getStatus = new GetStatus(mediaID);
			String statusResponse = "";
			try {
				getStatus.execute();
			} catch (IOException e1) {
				errorCount++;
				System.err.println("Erro #" + errorCount + " ao requisitar status da transferência do arquivo de vídeo\n" + e1.getMessage());
			}
			if(!getStatus.isValidResponse()){
				throw new RuntimeException("Não foi possível recuperar o status da a conversão.");
			}
			String status = getStatus.getStatus();
			if(status.equals("Ready to process")){
				videoPrepared = true;
				System.out.println("Video disponível para conversão.");
			}
			if(status.equals("Error")){
				throw new RuntimeException("Não foi possível tranferir o vídeo para o serviço de codificação.");
			}
			if(errorCount == maxErrorAccepted){
				throw new RuntimeException("Não foi possível obter o status da transferência do vídeo.");
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
		}
		
	}

}
