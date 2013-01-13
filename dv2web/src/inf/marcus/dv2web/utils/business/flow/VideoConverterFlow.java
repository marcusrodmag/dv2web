package inf.marcus.dv2web.utils.business.flow;

import inf.marcus.dv2web.utils.business.encoder.AddMedia;
import inf.marcus.dv2web.utils.business.encoder.MediaInformation;
import inf.marcus.dv2web.utils.business.encoder.ProcessMedia;

import java.io.IOException;

/**
 * Define o processo de convers�o de v�deo.
 * @author marcus
 *
 */
public class VideoConverterFlow {
	private String videoFileName;
	/**
	 * Apenas para estes.
	 */
	public static void main(String[] args) {
		VideoConverterFlow convert = new VideoConverterFlow("VID_20130112_160350_596.avi");
		convert.execute();
	}
	/**
	 * Defina o nome do arquivo a ser convertido e que j� se econtra no Servi�o de Armazenamento.
	 * @param videoFileName
	 */
	public VideoConverterFlow (String videoFileName){
		this.videoFileName = videoFileName;
	}
	/**
	 * Fluxo de execu��o de convers�o do v�deo.
	 */
	public void execute() {
		AddMedia addMedia = new AddMedia(this.videoFileName);
		try {
			addMedia.execute();
		} catch (IOException e) {
			System.err.println("Erro addMedia: " + e.getMessage());
			throw new RuntimeException("Erro ao adicionar arquivo de m�dia na fila de execu��o.");
		}
		System.out.println("Media ID = " + addMedia.getMediaID());
		new MonitoringMediaTransferFlow(addMedia.getMediaID()).monitor();
		MediaInformation mediaInfo = new MediaInformation(addMedia.getMediaID());
		try {
			mediaInfo.execute();
		} catch (IOException e) {
			System.err.println("Erro mediaInfo antes do processamento: " + e.getMessage());
			throw new RuntimeException("Erro ao obter informa��es sobre o arquivo de m�dia que acaba de ser enviada ao servido de convers�o.");
		}
		System.out.println(mediaInfo.toString());
		
		ProcessMedia processMedia = new ProcessMedia(addMedia.getMediaID());
		try {
			processMedia.execute();
		} catch (IOException e) {
			System.err.println("Erro processMedia: " + e.getMessage());
			throw new RuntimeException("N�o foi poss�vel converter o arquivo de v�deo ");
		}
		new MonitoringVideoConverterFlow(addMedia.getMediaID()).monitor();
		
		System.out.println("Fim do processo de convers�o de v�deo.");
	}

}
