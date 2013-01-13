package inf.marcus.dv2web.utils.exceptions;

/**
 * Falha ao converter o V�deo usando o servi�o encoding.com
 * @author marcus
 *
 */
public class EncodingConversionException extends Exception {
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 2L;
	/**
	 * Exception padr�o, exibindo apenas mensagem de erro, sem reproduzir a exce��o recebida.
	 * @param message
	 */
	public EncodingConversionException(String message){
		super(message);
	}
	/**
	 * Tamb�m infora o que causou o problema na covners�o do v�deo.
	 * @param message Mensagem para o usu�rio.
	 * @param cause Mais informa��es sobre o que causou o problema.
	 */
	public EncodingConversionException(String message, Throwable cause){
		super(message, cause);
	}
}
