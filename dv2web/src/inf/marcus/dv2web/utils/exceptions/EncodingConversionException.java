package inf.marcus.dv2web.utils.exceptions;

/**
 * Falha ao converter o Vídeo usando o serviço encoding.com
 * @author marcus
 *
 */
public class EncodingConversionException extends Exception {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2L;
	/**
	 * Exception padrão, exibindo apenas mensagem de erro, sem reproduzir a exceção recebida.
	 * @param message
	 */
	public EncodingConversionException(String message){
		super(message);
	}
	/**
	 * Também infora o que causou o problema na covnersão do vídeo.
	 * @param message Mensagem para o usuário.
	 * @param cause Mais informações sobre o que causou o problema.
	 */
	public EncodingConversionException(String message, Throwable cause){
		super(message, cause);
	}
}
