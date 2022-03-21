package us.pochaev.jsonapi.v1_0.converter.to.membername;

import java.util.HashSet;
import java.util.Set;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiSpecificationException;

/**
 * Validates member name conforms to <a href="https://jsonapi.org/format/#document-member-names">specification</a>
 *
 * @author apochaev
 *
 */
class Validator {

	public static String validate(String name) {
		validateContainsAtLeastOneCharacter(name);

		validateStartsAndEndsWithGloballyAllowedCharacters(name);

		validateContainsOnlyAllowedCharacters(name);

		return name;
	}

	private static void validateStartsAndEndsWithGloballyAllowedCharacters(String name) {
		char[] charArray = name.toCharArray();
		char startsWith = charArray[0];
		char endsWith = charArray[charArray.length - 1];

		if (CharactersAllowedGlobally.get().contains(Character.valueOf(startsWith)) &&
			CharactersAllowedGlobally.get().contains(Character.valueOf(endsWith))) {
			return;
		}

		throw new JsonApiSpecificationException( // TODO throw a more specific validation exception (value, offending position, etc.)
						"Member names MUST start and end with a “globally allowed character”.",
						"document-member-names-allowed-characters");
	}

	private static void validateContainsOnlyAllowedCharacters(String name) {
		Set<Character> allowed = new HashSet<>();
		allowed.addAll(CharactersAllowedGlobally.get());
		allowed.addAll(CharactersAllowedInMiddleOnly.get());

		for (char c : name.toCharArray()) {
			if (! allowed.contains(Character.valueOf(c))) {
				throw new JsonApiSpecificationException( // TODO throw a more specific validation exception (value, offending position, etc.)
						"Member names MUST contain only the allowed characters.",
						"document-member-names-allowed-characters");
			}
		}
	}

	private static void validateContainsAtLeastOneCharacter(String name) {
		if (name.length() < 1) {
			throw new JsonApiSpecificationException(
					"Member names MUST contain at least one character.",
					"document-member-names");
		}
	}

}
