package inf.marcus.dv2web.utils.formatter;



public abstract class EncodingCOMXMLFormatter extends GenericRequestXMLFromatter {
	/** Endereço para o request. */
	public static final String encoderRequestURL = "http://manage.encoding.com";
	
	/**
	 * Construtor que define a configuração básica de um XML Request.
	 * @param requesURL Enredeço para o qual o request será feito.
	 * @param userID Usuário usado para request.
	 * @param password Senha usada para autenticar o usuário.
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
