package inf.marcus.dv2web.utils.formatter;

import inf.marcus.dv2web.utils.business.encoder.MediaConverterRequester;
import inf.marcus.dv2web.utils.exceptions.EncodingConversionException;

public abstract class GenericRequestXMLFromatter {
	
	/** Usu�rio para o request. */
	private String userID;
	/** Senha para autentica��o do usu�rio. */
	private String password;
	/** XML gerado por esta classe. */
	private StringBuffer request;
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
		this.request = new StringBuffer();
	}
	
	/**
	 * Obtem o XML a ser utilizado no request.
	 * @return XML para request.
	 */
	private String buildXMLRequest(){
		this.createXMLHeader();
		this.createXMLBody();
		this.createXMLFooter();
		return this.request.toString();
	}
	
	/**
	 * Executa o request utilizando o XML contruido por esta classe utilit�ria.
	 */
	public void execute() throws EncodingConversionException{
		System.out.println();
		System.out.println("Request Class >>> " + this.getClass().getName());
		String xmlRequest = this.buildXMLRequest();
		/*
		 * Permite exeutar v�rias tentativas ap�s uma indisponibilidade do servidor de convers�o.
		 */
		// N�mero m�ximo de tentativas de requests antes de interromper processamento.
		int maxNumberAttempts = 3;
		// Contador de erros de request.
		int countError = 0;
		// Controlador do loop para tentativas.
		boolean successRequest = false;
		while(!successRequest){
			if(countError == maxNumberAttempts){
				throw new EncodingConversionException("N�mero m�ximo de tentativas obtida enquanto tentava enviar requisi��o para o servi�o.");
			}
			String response = null;
			try {
				response = MediaConverterRequester.executeQuery(xmlRequest);
				this.setResponse(response);
				successRequest = true;
			} catch (EncodingConversionException e){
				countError++;
				System.err.println("Erro #"+ countError + " ao realizar request.");
				//Aguardar antes de tentar novo request.
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {	}
			}
		}

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
	 * Valida o resultado do request.
	 * @param Resultado da execu��o do request.
	 * @return True se o resultado foi positivo.
	 */
	public abstract boolean isValidResponse();
	
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
		this.request.append(append);
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
}
