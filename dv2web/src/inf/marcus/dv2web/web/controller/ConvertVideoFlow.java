package inf.marcus.dv2web.web.controller;

import inf.marcus.dv2web.utils.business.encoder.AddMedia;
import inf.marcus.dv2web.utils.business.encoder.EncodingRequester;
import inf.marcus.dv2web.utils.business.encoder.MediaInformation;
import inf.marcus.dv2web.utils.business.encoder.ProcessMedia;
import inf.marcus.dv2web.utils.business.flow.MonitoringMediaTransfer;
import inf.marcus.dv2web.web.business.AmazonWSS3;

import java.io.IOException;


public class ConvertVideoFlow {
	
	public static void main(String[] args) {
		ConvertVideoFlow convert = new ConvertVideoFlow();
		convert.execute();
	}

	public void execute() {
//		AmazonWSS3 awsInfo = new AmazonWSS3("sample2.avi");
//		AddMedia addMedia = new AddMedia(awsInfo.getMediaSourceURL(), awsInfo.getMediaDestinationURL());
//		String responseAddMedia = null;
//		try {
//			responseAddMedia = EncodingRequester.executeQuery(addMedia.getXMLRequest());
//		} catch (IOException e) {
//			System.err.println("Não foi possível enviar requisição para o Serviço de conversão de vídeo:\n" + e.getMessage());
//		}
//		System.out.println("Media ID = " + addMedia.getMediaID(responseAddMedia));
//		MonitoringMediaTransfer mon = new MonitoringMediaTransfer(addMedia.getMediaID(responseAddMedia));
//		mon.monitor();
		MediaInformation mediaInfo = new MediaInformation(14993985);
		try {
			mediaInfo.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mediaInfo.toString());
		
		ProcessMedia processMedia = new ProcessMedia(14993985);
		try {
			processMedia.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MediaInformation mediaInfoPosProccess = new MediaInformation(14993985);
		try {
			mediaInfoPosProccess.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mediaInfoPosProccess.toString();
	}

}
