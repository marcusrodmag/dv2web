package inf.marcus.dv2web.utils.business.encoder;

import inf.marcus.dv2web.utils.formatter.EncodingCOMXMLFormatter;

public class ProcessMedia extends EncodingCOMXMLFormatter {
	int mediaID;
	
	public ProcessMedia(int mediaID){
		this.mediaID = mediaID;
	}
	
	
	@Override
	public void createXMLBody() {
		super.xmlBuild("<action>ProcessMedia</action>");
		super.xmlBuild("<mediaid>"+ mediaID +"</mediaid>");
	}

	@Override
	public boolean isValidResponse() {
		// TODO Auto-generated method stub
		return false;
	}

}
