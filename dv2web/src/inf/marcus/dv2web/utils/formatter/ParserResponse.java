package inf.marcus.dv2web.utils.formatter;

public class ParserResponse {
	private String response;
	public ParserResponse(String response) {
		this.response = response;
	}
	
	public String getValue(String fieldName){
		if(!response.contains(fieldName)){
			System.err.println("O campo não foi encontrado no response: " + fieldName);
			return "";
		}
		String openTag = "<"+fieldName+">";
		String closeTag = "</"+fieldName+">";
	    int start = response.indexOf(openTag) + openTag.length();
	    int end = response.indexOf(closeTag);
	    return response.substring(start, end);
	}

}
