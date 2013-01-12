package inf.marcus.dv2web.utils.business.encoder;

import inf.marcus.dv2web.utils.formatter.EncodingCOMXMLFormatter;
import inf.marcus.dv2web.utils.formatter.ParserResponse;

public class MediaInformation extends EncodingCOMXMLFormatter {
	
	private int mediaID;
	private String bitrate;
	private String duration;
	private String videoCodec;
	private String size;
	private String audioCodec;
	private String audioChannels;
	
	public MediaInformation(int mediaID){
		this.mediaID = mediaID;
	}

	@Override
	public void createXMLBody() {
		super.xmlBuild("<action>GetMediaInfo</action>");
		super.xmlBuild("<mediaid>"+ mediaID +"</mediaid>");
	}
	/**
	 * Template do response
	 * 
	 *     <?xml version="1.0"?>  
	 *     <response>
	 *         <bitrate>1807k</bitrate>
	 *         <duration>6464.83</duration>
	 *         <video_codec>mpeg4</video_codec>
	 *         <video_bitrate>1679k</video_bitrate>
	 *         <frame_rate>23.98</frame_rate>
	 *         <size>640x352</size><pixel_aspect_ratio>1:1</pixel_aspect_ratio>
	 *         <display_aspect_ratio>20:11</display_aspect_ratio>
	 *         <audio_codec>ac3</audio_codec><audio_sample_rate>48000</audio_sample_rate>
	 *         <audio_channels>2</audio_channels>
	 *    </response>  
	 */

	@Override
	public boolean isValidResponse() {
		if(super.getResponse() != null){
			return super.getResponse().contains("<response><bitrate>");
		}
		return false;
	}
	
	@Override
	public String toString(){
		if(!this.isValidResponse()){
			System.err.println("Erro ao recuperar informações da midia. ID" + mediaID);
			return null;
		}
		this.parserResponse();
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("\nInformações sobre formato da midia em processamento  #" + mediaID);
		strBuilder.append("\n\tBitrate: " + this.getBitrate());
		strBuilder.append("\n\tDuração: " + this.getDuration());
		strBuilder.append("\n\tTamanho: " + this.getSize());
			
		strBuilder.append("\n\tCodec de Vídeo: " + this.getVideoCodec());
		strBuilder.append("\n\tCodec de Áudio: " + this.getAudioCodec());
		strBuilder.append("\n\tCanais de áudio: " + this.getAudioChannels());
		return strBuilder.toString();
	}
	
	private void parserResponse(){
		ParserResponse parser = new ParserResponse(super.getResponse());
		this.setBitrate(parser.getValue("bitrate"));
		this.setDuration(parser.getValue("duration"));
		this.setVideoCodec(parser.getValue("video_codec"));
		this.setSize(parser.getValue("size"));
		this.setAudioCodec(parser.getValue("audio_codec"));
		this.setAudioChannels(parser.getValue("audio_channels"));
	}
	public String getBitrate() {
		return bitrate;
	}

	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}


	public String getDuration() {
		return duration;
	}


	public void setDuration(String duration) {
		this.duration = duration;
	}


	public String getVideoCodec() {
		return videoCodec;
	}


	public void setVideoCodec(String videoCodec) {
		this.videoCodec = videoCodec;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getAudioCodec() {
		return audioCodec;
	}


	public void setAudioCodec(String audioCodec) {
		this.audioCodec = audioCodec;
	}


	public String getAudioChannels() {
		return audioChannels;
	}


	public void setAudioChannels(String audioChannels) {
		this.audioChannels = audioChannels;
	}

}
