package inf.marcus.dv2web.utils.business.encoder;

import inf.marcus.dv2web.utils.formatter.EncodingCOMXMLFormatter;

public class AddMedia extends EncodingCOMXMLFormatter {
	
	private String sourceMedia;
	private String destinationMediaURL;
    private final String successResponseStartTag = "<response><message>Added</message><MediaID>";
    private final String successResponseEndTag = "</MediaID></response>";
	
    /**
     * Adiciona um arquivo de média na fila de conversão.
     * @param sourceMedia Arquivo de entrada.
     * @param destinationMediaURL Arquivo de saída.
     */
	public AddMedia(String sourceMedia, String destinationMediaURL){
		this.sourceMedia = sourceMedia;
		this.destinationMediaURL = destinationMediaURL;
	}

	@Override
	public void createXMLBody() {
		super.xmlBuild("<action>AddMediaBenchmark</action>");
		super.xmlBuild("<source>" + this.sourceMedia + "</source>");
		super.xmlBuild("<format>");
		super.xmlBuild("<destination>" + this.destinationMediaURL + "</destination>");
		super.xmlBuild("<output>mp4</output>");
		super.xmlBuild("<video_codec>mpeg4</video_codec>");
		super.xmlBuild("<audio_codec>libfaac</audio_codec>");
		super.xmlBuild("<bitrate>256k</bitrate >");
		super.xmlBuild("</format>");
	}

	@Override
	public boolean isValidResponse() {
		if(this.getResponse() != null){
			if( this.getResponse().contains(this.successResponseEndTag) && this.getResponse().contains(this.successResponseStartTag)){
				return true;
			}
		}
		return false;
	}

	/** Recupera o identificado do arquivo de vídeo que foi adicionado no processo de conversão de vídeo. 
	 * @return Identificador único da media a ser convertida.
	 * @param Resultado da execução da requisição.
	 */
	public int getMediaID() {
		if(!this.isValidResponse()){
			System.err.println(super.getResponse());
			throw new RuntimeException("Não foi possível adicionar o arquivo de média a fila de exeução pois a requisição foi negado para Serviço de Conversão.");
		}
	    int start = super.getResponse().indexOf(successResponseStartTag) + successResponseStartTag.length();
	    int end = super.getResponse().indexOf(successResponseEndTag);
	    return Integer.parseInt(super.getResponse().substring(start, end));
	}

}
