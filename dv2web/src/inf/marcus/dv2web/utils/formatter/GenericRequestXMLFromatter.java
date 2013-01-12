package inf.marcus.dv2web.utils.formatter;

import inf.marcus.dv2web.utils.business.encoder.EncodingRequester;

import java.io.IOException;


public abstract class GenericRequestXMLFromatter {
	
	/** Usu�rio para o request. */
	private String userID;
	/** Senha para autentica��o do usu�rio. */
	private String password;
	/** XML gerado por esta classe. */
	private StringBuffer xml;
	/** O XML resultante da requisi��o do XML gerado por esta classe. */
	private String response;

	/**
	 * Construtor que define a configura��o b�sica de um XML Request.
	 * @param requesURL Enrede�o para o qual o request ser� feito.
	 * @param userID Usu�rio usado para request.
	 * @param password Senha usada para autenticar o usu�rio.
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
	 * Executa o request do XML montado para este servi�o.
	 */
	public void execute() throws IOException{
		this.setResponse(EncodingRequester.executeQuery(this.getXMLRequest()));
	}
	/**
	 * Define o cabe�alho do XML a ser configurado para request.
	 */
	protected abstract void createXMLHeader();
	/**
	 * Define o rodap� do XML a ser configurado para request.
	 */
	protected abstract void createXMLFooter();
	
	/**
	 * Gera o XML que ficar� entre o Cabe�alho e o Rodap� do XML.
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
	 * @param Resultado da execu��o do request.
	 * @return True se o resultado foi positivo.
	 */
	public abstract boolean isValidResponse();
	
}
