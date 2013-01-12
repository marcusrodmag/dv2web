package inf.marcus.dv2web.utils.business.encoder;

import inf.marcus.dv2web.utils.formatter.EncodingCOMXMLFormatter;

public class GetStatus extends EncodingCOMXMLFormatter {
	private int mediaID;
	private String successResponseStartTag = "<status>";
	private String successResponseEndTag = "</status>";
	private String status;
	
	
	public GetStatus (int mediaID) {
		this.mediaID = mediaID;
	}

	@Override
	public void createXMLBody() {
		super.xmlBuild("<action>GetStatus</action>");
		super.xmlBuild("<mediaid>"+ mediaID +"</mediaid>");
	}

	@Override
	public boolean isValidResponse() {
		if(this.getResponse().contains(successResponseStartTag) && this.getResponse().contains(successResponseStartTag)){
			return true;
		}
		return false;
	}
	
	public String getStatus(){
		if(!this.isValidResponse()){
			return null;
		}
		System.out.println("Verificando status de transferência entre AWS e encoding.com");
	    int start = super.getResponse().indexOf(successResponseStartTag) + successResponseStartTag.length();
	    int end = super.getResponse().indexOf(successResponseEndTag);
	    status = super.getResponse().substring(start, end);
	    System.out.println("Status: " + status);
	    return status;
	}

}
