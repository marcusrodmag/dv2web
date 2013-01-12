package inf.marcus.dv2web.utils.formatter;

import inf.marcus.dv2web.utils.business.encoder.EncodingRequester;

import java.io.IOException;


public abstract class GenericRequestXMLFromatter {
	
	/** Usuário para o request. */
	private String userID;
	/** Senha para autenticação do usuário. */
	private String password;
	/** XML gerado por esta classe. */
	private StringBuffer xml;
	/** O XML resultante da requisição do XML gerado por esta classe. */
	private String response;

	/**
	 * Construtor que define a configuração básica de um XML Request.
	 * @param requesURL Enredeço para o qual o request será feito.
	 * @param userID Usuário usado para request.
	 * @param password Senha usada para autenticar o usuário.
	 */
	public GenericRequestXMLFromatter(String userID, String password){
		this.userID = userID;
		this.password = password;
		this.xml = new StringBuffer();
	}
	
	/**
	 * Obtem o XML a ser utilizado no request.
	 * @return XML para request.
	 */
	private String getXMLRequest(){
		this.createXMLHeader();
		this.createXMLBody();
		this.createXMLFooter();
		return this.xml.toString();
	}
	
	/**
	 * Executa o request do XML montado para este serviço.
	 */
	public void execute() throws IOException{
		this.setResponse(EncodingRequester.executeQuery(this.getXMLRequest()));
	}
	/**
	 * Define o cabeçalho do XML a ser configurado para request.
	 */
	protected abstract void createXMLHeader();
	/**
	 * Define o rodapé do XML a ser configurado para request.
	 */
	protected abstract void createXMLFooter();
	
	/**
	 * Gera o XML que ficará entre o Cabeçalho e o Rodapé do XML.
	 */
	public abstract void createXMLBody();
	
	public String  getUserID() {
		return this.userID;
	}
	public String getPassword() {
		return this.password;
	}
	public void xmlBuild(String append){
		this.xml.append(append);
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	/**
	 * Valida o resultado do request.
	 * @param Resultado da execução do request.
	 * @return True se o resultado foi positivo.
	 */
	public abstract boolean isValidResponse();
	
}
