package inf.marcus.dv2web.utils.formatter;



public abstract class EncodingCOMXMLFormatter extends GenericRequestXMLFromatter {
	/** Endere�o para o request. */
	public static final String encoderRequestURL = "http://manage.encoding.com";
	
	/**
	 * Construtor que define a configura��o b�sica de um XML Request.
	 * @param requesURL Enrede�o para o qual o request ser� feito.
	 * @param userID Usu�rio usado para request.
	 * @param password Senha usada para autenticar o usu�rio.
	 */
	public EncodingCOMXMLFormatter(){
		super("17085", "3fb21a0e7a8e6c1b2d9950d8bf7eb289");
	}

	@Override
	protected void createXMLHeader() {
		this.xmlBuild("<?xml version='1.0'?>");
		this.xmlBuild("<query>");
		this.xmlBuild("<userid>" + super.getUserID() + "</userid>");
		this.xmlBuild("<userkey>" + super.getPassword() + "</userkey>");
	}
	
	@Override
	protected void createXMLFooter() {
		this.xmlBuild("</query>");
	}

	@Override
	public abstract void createXMLBody();
	
}
