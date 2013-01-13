package inf.marcus.dv2web.utils.business.encoder;

import inf.marcus.dv2web.utils.exceptions.EncodingConversionException;
import inf.marcus.dv2web.utils.exceptions.VideoConversionException;
import inf.marcus.dv2web.utils.formatter.EncodingCOMXMLFormatter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MediaConverterRequester {
	
	/** Nenhum contrutor padrão. */
	private MediaConverterRequester(){}
	/**
	 * Envia uma requisição para o serviço de conversão de vídeo e retorna o resultado no formato XML.
	 * @param requestURL Endereço para request.
	 * @param xml o XML com a query
	 * @return Resultado da execução do request.
	 * @throws VideoConversionException caso ocorra algum problema na transação entre os servidores de arquivo e conversão.
	 */
	public static String executeQuery(String xml) throws EncodingConversionException {
		System.out.println();
		System.out.println("====================================================");
		URL encoderURL;
		try {
			encoderURL = new URL(EncodingCOMXMLFormatter.encoderRequestURL);
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
		System.out.println();
		System.out.println("REQUEST >>");
		System.out.println(xml);
		System.out.println();
		try{
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
			System.out.println("TRANSACTION: " + urlConnection.getResponseCode() + " ["+urlConnection.getResponseMessage()+"]");
			
			strbuf = new StringBuffer();
			byte[] buffer = new byte[1024 * 4];
			
			int n = 0;
			while ((n = is.read(buffer)) != -1) {
				strbuf.append(new String(buffer, 0, n));
			}
			is.close();
		} catch (IOException e){
			throw new EncodingConversionException("Erro ao solicitar informações ao servidor de conversão de vídeo: \n", e);
		}
		System.out.println();
		System.out.println("RESPONSE >>");
		System.out.println(strbuf.toString());
		System.out.println();
		System.out.println("====================================================");
		return strbuf.toString();

	}

}
