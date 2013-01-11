package inf.marcus.dv2web.jsp.business;

import java.io.*;
import java.net.*;

/**
 * Utiliza o serviço encoding.com para codificar o vídeo.
 * @author marcus
 *
 */
public class EncodingCom {

	private final String userID = "17059";
	private final String userKey = "a871b355e6d07ef25e7f3c2e110eea1a";
	private final String encoderService = "http://manage.encoding.com";
	private String filename = null;
	private AmazonWSS3 awsInfo = null;
	/**
	 * Contrutor padrão com nome do arquivo a ser convertido.
	 * @param videoFilename nome do arquivo localizado no AWS S3
	 */
	public EncodingCom(String videoFilename){
		this.filename = videoFilename;
		this.awsInfo = new AmazonWSS3(this.filename);
		System.out.println("Conectando ao servidor de encoding: " + this.encoderService);
		System.out.println("\tArquivo Fonte:\t\t" + awsInfo.getMediaSourceURL());
		System.out.println("\tArquivo Destino\t: 	" + awsInfo.getMediaDestinationURL());
	}
	/**
	 * Executa o fluxo de conversão utilizando o serviço encoding.com
	 * Doc: http://www.encoding.com/api/category/category/complete_api_documentation#main_fields
	 * @return mediaID do vídeo cadastrado.
	 */
	private int addMedia() {
	    String successResponseStartTag = "<response><message>Added</message><MediaID>";
	    String successResponseEndTag = "</MediaID></response>";
		int mediaID = -1;

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version='1.0'?>");
		xml.append("<query>");
		xml.append("<userid>" + this.userID + "</userid>");
		xml.append("<userkey>" + this.userKey + "</userkey>");
		xml.append("<action>AddMedia</action>");
		xml.append("<source>" + awsInfo.getMediaSourceURL() + "</source>");
		xml.append("<destination>" + awsInfo.getMediaDestinationURL() + "</destination>");
		xml.append("<format>");
		xml.append("<output>mp4</output>");
//		xml.append("<bitrate>3gp</bitrate >");
//		xml.append("<video_codec>mp4</video_codec>");
//		xml.append("<audio_codec>mp3</audio_codec>");
		
		xml.append("</format>");
		xml.append("</query>");
		
		String xmlResponse;
		try {
			xmlResponse = this.executeQuery(xml);
		} catch (IOException e) {
			System.err.println("Erro ao enviar requisição de cadastro de Arquivo para conversão");
			e.printStackTrace();
			return -1;
		}
		if(xmlResponse.contains("<response><message>Added</message><MediaID>")){
			System.out.println("Video adicionado na fila de execução.");
			System.out.println("\tResultado do comando:");
			System.out.println(xmlResponse);
		    int start = xmlResponse.indexOf(successResponseStartTag) + successResponseStartTag.length();
		    int end = xmlResponse.indexOf(successResponseEndTag);
		    mediaID = Integer.parseInt(xmlResponse.substring(start, end));
		    System.out.println("MediaID retornada: " + mediaID);
		} else {
			System.out.println("O Serviço de encoding retornou um resultado não esperado:");
			System.out.println(xmlResponse);
			return -2;
		}
		return mediaID;
	}

	
	public static void main(String[] args) {
		EncodingCom encoding = new EncodingCom("sample.dv");
		int mediaID = encoding.addMedia();
		if(mediaID<1){
			System.out.println("Erro ao adicionar arquivo de midia para conversão.");
			System.exit(1);
		}
		encoding.processMedia(mediaID);
	}
	private void processMedia(int mediaID) {
		URL encoderURL = null;
	    String successResponseStartTag = "<response><message>Added</message><MediaID>";
	    String successResponseEndTag = "</MediaID></response>";
		AmazonWSS3 awsInfo = new AmazonWSS3(filename);
		StringBuffer strbuf = null;

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version='1.0'?>");
		xml.append("<query>");
		xml.append("<userid>" + this.userID + "</userid>");
		xml.append("<userkey>" + this.userKey + "</userkey>");
		xml.append("<action>ProcessMedia</action>");
		xml.append("<mediaid>"+ mediaID +"</mediaid>");
		xml.append("</query>");
		
		String response;
		try {
			response = this.executeQuery(xml);
		} catch (IOException e) {
			System.err.println("Erro ao requisitar processamento de conversão do vídeo");
			e.printStackTrace();
			return;
		}
		System.out.println("Response sobre processar: ");
		System.out.println(response);
	}
	/**
	 * Envia uma requisição para o serviço de conversão de vídeo e retorna o resultado no formato XML.
	 * @param xml o XML com a query
	 * @return XML com o resultado da execução da query
	 * @throws IOException caso ocorra algum problema na transação entre os servidores de arquivo e conversão.
	 */
	private String executeQuery(StringBuffer xml) throws IOException {
		URL encoderURL;
		try {
			encoderURL = new URL(encoderService);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
		StringBuffer strbuf = null;
		String sRequest = null;
		try {
			sRequest = "xml=" + URLEncoder.encode(xml.toString(), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("Enviando requisição para serviço de conversão:");
		System.out.println(xml);
		HttpURLConnection urlConnection = (HttpURLConnection) encoderURL.openConnection();
		urlConnection.setRequestMethod("POST");
		urlConnection.setDoOutput(true);
		urlConnection.setConnectTimeout(60000);
		urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
		out.write(sRequest);
		out.flush();
		out.close();
		urlConnection.connect();
		InputStream is = urlConnection.getInputStream();
		String str = urlConnection.getResponseMessage();
		System.out.println("Response:" + urlConnection.getResponseCode() + " ["+urlConnection.getResponseMessage()+"]");
		
		strbuf = new StringBuffer();
		byte[] buffer = new byte[1024 * 4];

		int n = 0;
		while ((n = is.read(buffer)) != -1) {
			strbuf.append(new String(buffer, 0, n));
		}
		is.close();
		return strbuf.toString();

	}

}
