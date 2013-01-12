package inf.marcus.dv2web.utils.business.encoder;

import inf.marcus.dv2web.utils.formatter.EncodingCOMXMLFormatter;

public class AddMedia extends EncodingCOMXMLFormatter {
	
	private String sourceMedia;
	private String destinationMediaURL;
    private final String successResponseStartTag = "<response><message>Added</message><MediaID>";
    private final String successResponseEndTag = "</MediaID></response>";
	
	public AddMedia(String sourceMedia, String destinationMediaURL){
		this.sourceMedia = sourceMedia;
		this.destinationMediaURL = destinationMediaURL;
	}

	@Override
	public void createXMLBody() {
		super.xmlBuild("<action>AddMediaBenchmark</action>");
		super.xmlBuild("<source>" + this.sourceMedia + "</source>");
		super.xmlBuild("<destination>" + this.destinationMediaURL + "</destination>");
		super.xmlBuild("<format>");
		super.xmlBuild("<output>mp4</output>");
//		super.xmlBuild("<bitrate>3gp</bitrate >");
//		super.xmlBuild("<video_codec>mp4</video_codec>");
//		super.xmlBuild("<audio_codec>mp3</audio_codec>");
		super.xmlBuild("</format>");
	}

	@Override
	public boolean isValidResponse() {
		if( !this.getResponse().contains(this.successResponseEndTag) || !this.getResponse().contains(this.successResponseStartTag)){
			return false;
		}
		return true;
	}

	/** Recupera o identificado do arquivo de vídeo que foi adicionado no processo de conversão de vídeo. 
	 * @return Identificador único da media a ser convertida.
	 * @param Resultado da execução da requisição.
	 */
	public int getMediaID(String response) {
		if(!this.isValidResponse()){
			return -1;
		}
	    int start = response.indexOf(successResponseStartTag) + successResponseStartTag.length();
	    int end = response.indexOf(successResponseEndTag);
	    return Integer.parseInt(response.substring(start, end));
	}

}
