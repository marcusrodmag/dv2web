package inf.marcus.dv2web.utils.business.encoder;

import inf.marcus.dv2web.utils.constants.ConstantsAWS;
import inf.marcus.dv2web.utils.exceptions.EncodingConversionException;
import inf.marcus.dv2web.utils.formatter.EncodingCOMXMLFormatter;
import inf.marcus.dv2web.web.business.DV2WEBFileUtils;

public class AddMedia extends EncodingCOMXMLFormatter {
	
	private String fileName;
    private final String successResponseStartTag = "<response><message>Added</message><MediaID>";
    private final String successResponseEndTag = "</MediaID></response>";
	
    /**
     * Adiciona um arquivo de m�dia na fila de convers�o.
     * @param sourceMedia Arquivo de entrada.
     * @param destinationMediaURL Arquivo de sa�da.
     */
	public AddMedia(String fileName){
		this.fileName = fileName;
	}

	@Override
	public void createXMLBody() {
		super.xmlBuild("<action>AddMediaBenchmark</action>");
		super.xmlBuild("<source>" + this.getMediaSourceURL() + "</source>");
		super.xmlBuild("<format>");
		super.xmlBuild("<destination>" + this.getMediaDestinationURL() + "</destination>");
		super.xmlBuild("<output>wmv</output>");
		super.xmlBuild("<video_codec>wmv2</video_codec>");
		super.xmlBuild("<audio_codec>wmav2</audio_codec>");
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

	/** Recupera o identificado do arquivo de v�deo que foi adicionado no processo de convers�o de v�deo. 
	 * @return Identificador �nico da media a ser convertida.
	 * @param Resultado da execu��o da requisi��o.
	 */
	public int getMediaID() throws EncodingConversionException {
		if(!this.isValidResponse()){
			System.err.println(super.getResponse());
			throw new EncodingConversionException("N�o foi poss�vel adicionar o arquivo de m�dia a fila de exeu��o pois a requisi��o foi negado para Servi�o de Convers�o.");
		}
	    int start = super.getResponse().indexOf(successResponseStartTag) + successResponseStartTag.length();
	    int end = super.getResponse().indexOf(successResponseEndTag);
	    return Integer.parseInt(super.getResponse().substring(start, end));
	}
	
	private String getMediaDestinationURL() {
		return "http://"+ConstantsAWS.AWS_S3_BUCKET_NAME+".s3.amazonaws.com/"+ConstantsAWS.AWS_S3_BUCKET_ENCODED_SUBDIR+ "/" +new DV2WEBFileUtils(fileName).getConvertedFileName()+"?acl=public-read";
	}
	
	private String getMediaSourceURL() {
		return "http://"+ConstantsAWS.AWS_S3_BUCKET_NAME+".s3.amazonaws.com/" + ConstantsAWS.AWS_S3_BUCKET_ORIGINAL_SUBDIR + "/" + this.fileName;
	}

}
