package inf.marcus.dv2web.utils.formatter;

import inf.marcus.dv2web.utils.business.encoder.MediaConverterRequester;

import java.io.IOException;

import sun.awt.windows.ThemeReader;

public abstract class GenericRequestXMLFromatter {
	
	/** Usuário para o request. */
	private String userID;
	/** Senha para autenticação do usuário. */
	private String password;
	/** XML gerado por esta classe. */
	private StringBuffer request;
	/** O XML resultante da requisição do XML gerado por esta classe. */
	private String response;
	
	/** Define o número máximo de tentativas que serão feitas antes de retornar erro para quem esta ordenando a requisição. */
	private int maxNumberAttempts = 3;

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
	 * Executa o request do XML montado para este serviço.
	 */
	public void execute() throws IOException, RuntimeException{
		System.out.println();
		System.out.println("Request Class >>> " + this.getClass().getName());
		int countError = 0;
		boolean successRequest = false;

			while(!successRequest){
				if(countError == maxNumberAttempts){
					throw new RuntimeException("Número máximo de tentativas obtida enquanto tentava enviar requisição para o serviço.");
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
