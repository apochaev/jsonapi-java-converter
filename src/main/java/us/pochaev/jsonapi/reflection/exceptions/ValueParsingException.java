package us.pochaev.jsonapi.reflection.exceptions;

public class ValueParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a ValueParsingException with the specified message.
	 * @see {@link RuntimeException#RuntimeException(String)}
	 */
	public ValueParsingException(String message) {
		super(message);
	}

	/**
	 * Creates a ValueParsingException with the specified cause.
	 * @see {@link RuntimeException#RuntimeException(Throwable)}
	 */
	public ValueParsingException(Throwable cause) {
		super(cause);
	}

}
