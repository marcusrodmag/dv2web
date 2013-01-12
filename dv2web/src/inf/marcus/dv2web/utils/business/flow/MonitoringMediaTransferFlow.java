package inf.marcus.dv2web.utils.business.flow;

import inf.marcus.dv2web.utils.business.encoder.GetStatus;

import java.io.IOException;

public class MonitoringMediaTransferFlow {
	private int mediaID;
	
	public MonitoringMediaTransferFlow(int mediaID){
		this.mediaID = mediaID;
	}

	public void monitor(){
		int errorCount = 0;
		int maxErrorAccepted = 10;
		boolean videoPrepared = false;
		System.out.println("Aguardando trasnferênciado arquivo.");
		while (!videoPrepared) {
			if(errorCount == maxErrorAccepted){
				throw new RuntimeException("Número máximo de tentativas para realizar a requisição ao serviço.");
			}
			GetStatus getStatus = new GetStatus(mediaID);
			try {
				getStatus.execute();
			} catch (IOException e1) {
				errorCount++;
				System.err.println("Erro #" + errorCount + ". Falhar ao requisitar status da transferência do arquivo de vídeo\n" + e1.getMessage());
				continue;
			}
			if(!getStatus.isValidResponse()){
				errorCount++;
				System.err.println("Erro #" + errorCount + ". Retorno de status inválido\n");
				continue;
			}
			String status = getStatus.getStatus();
			if(status.equals("Ready to process")){
				videoPrepared = true;
				System.out.println("Video disponível para conversão.");
			}
			if(status.equals("Error")){
				throw new RuntimeException("Não foi possível tranferir o vídeo para o serviço de codificação.");
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {}
		}
		
	}

}
