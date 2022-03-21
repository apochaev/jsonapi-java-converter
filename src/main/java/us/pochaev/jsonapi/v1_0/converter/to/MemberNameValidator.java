package us.pochaev.jsonapi.v1_0.converter.to;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiSpecificationException;

public class MemberNameValidator {

	public static String validate(String name) {
		if (name.length() < 1) {
			String message = "Member names MUST contain at least one character.";
			throw memberNameValidationException(message);
		}
		return name;
	}

	private static JsonApiSpecificationException memberNameValidationException(String message) {
		return new JsonApiSpecificationException(message,
				"document-member-names");
	}
}
