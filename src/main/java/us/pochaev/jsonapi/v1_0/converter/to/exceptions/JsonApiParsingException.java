package us.pochaev.jsonapi.v1_0.converter.to.exceptions;

public class JsonApiParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JsonApiParsingException(String message) {
		super(message);
	}

	public JsonApiParsingException(Throwable cause) {
		super(cause);
	}

	public JsonApiParsingException(String message, Throwable cause) {
		super(message, cause);
	}
}
