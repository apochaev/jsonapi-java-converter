package us.pochaev.jsonapi.v1_0.converter.to.membername;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides a static unmodifiable set of characters that are allowed in member names,
 * except as the first or last character.
 * The set excludes not recommended characters.
 *
 * @author apochaev
 *
 */
class CharactersAllowedInMiddleOnly {
	private static Set<Character> allowed  = initializeAllowed();

	private static Set<Character> initializeAllowed() {
		Set<Character> allowed = new HashSet<>();

		allowed.add(Character.valueOf('-'));
		allowed.add(Character.valueOf('_'));

		return Collections.unmodifiableSet(allowed);
	}

	public static Set<Character> get() {
		return allowed;
	}

}
