package plateforme.action;

public class WrongActionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongActionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public WrongActionException(String message) {
		super(message);
	}
	
}
