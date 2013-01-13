package inf.marcus.dv2web.utils.formatter;

import inf.marcus.dv2web.utils.business.encoder.MediaConverterRequester;
import inf.marcus.dv2web.utils.exceptions.EncodingConversionException;

public abstract class GenericRequestXMLFromatter {
	
	/** Usuário para o request. */
	private String userID;
	/** Senha para autenticação do usuário. */
	private String password;
	/** XML gerado por esta classe. */
	private StringBuffer request;
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
	 * Executa o request utilizando o XML contruido por esta classe utilitária.
	 */
	public void execute() throws EncodingConversionException{
		System.out.println();
		System.out.println("Request Class >>> " + this.getClass().getName());
		String xmlRequest = this.buildXMLRequest();
		/*
		 * Permite exeutar várias tentativas após uma indisponibilidade do servidor de conversão.
		 */
		// Número máximo de tentativas de requests antes de interromper processamento.
		int maxNumberAttempts = 3;
		// Contador de erros de request.
		int countError = 0;
		// Controlador do loop para tentativas.
		boolean successRequest = false;
		while(!successRequest){
			if(countError == maxNumberAttempts){
				throw new EncodingConversionException("Número máximo de tentativas obtida enquanto tentava enviar requisição para o serviço.");
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
	 * Define o cabeçalho do XML a ser configurado para request.
	 */
	protected abstract void createXMLHeader();
	/**
	 * Define o rodapé do XML a ser configurado para request.
	 */
	protected abstract void createXMLFooter();
	/**
	 * Valida o resultado do request.
	 * @param Resultado da execução do request.
	 * @return True se o resultado foi positivo.
	 */
	public abstract boolean isValidResponse();
	
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
		this.request.append(append);
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
}
