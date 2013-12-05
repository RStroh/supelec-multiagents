package plateforme.action;

public class UndefinedActionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UndefinedActionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UndefinedActionException(String message) {
		super(message);
	}
	
}
