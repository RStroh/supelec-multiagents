package plateforme.interaction;

public class SendMessageException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SendMessageException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SendMessageException(String message) {
		super(message);
	}
	
}
