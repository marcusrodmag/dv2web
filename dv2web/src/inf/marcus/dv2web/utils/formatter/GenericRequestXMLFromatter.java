package inf.marcus.dv2web.utils.formatter;

import inf.marcus.dv2web.utils.business.encoder.MediaConverterRequester;

import java.io.IOException;

import sun.awt.windows.ThemeReader;

public abstract class GenericRequestXMLFromatter {
	
	/** Usu�rio para o request. */
	private String userID;
	/** Senha para autentica��o do usu�rio. */
	private String password;
	/** XML gerado por esta classe. */
	private StringBuffer request;
	/** O XML resultante da requisi��o do XML gerado por esta classe. */
	private String response;
	
	/** Define o n�mero m�ximo de tentativas que ser�o feitas antes de retornar erro para quem esta ordenando a requisi��o. */
	private int maxNumberAttempts = 3;

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
	 * Executa o request do XML montado para este servi�o.
	 */
	public void execute() throws IOException, RuntimeException{
		System.out.println();
		System.out.println("Request Class >>> " + this.getClass().getName());
		int countError = 0;
		boolean successRequest = false;

			while(!successRequest){
				if(countError == maxNumberAttempts){
					throw new RuntimeException("N�mero m�ximo de tentativas obtida enquanto tentava enviar requisi��o para o servi�o.");
				}
				String response = null;
				try {
					response = MediaConverterRequester.executeQuery(this.buildXMLRequest());
				} catch (IOException e){
					countError++;
					System.err.println("Erro #"+ countError + " ao realizar request.");
					//Aguardar 2 segundos antes de tentar novo request.
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {	}
				}
				int i = 0;
				this.setResponse(response);
				successRequest = true;
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
