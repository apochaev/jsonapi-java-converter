package us.pochaev.jsonapi.v1_0.converter.to.membername;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides a static unmodifiable set of characters that are allowed in member names.
 * The set excludes not recommended characters.
 *
 * @author apochaev
 *
 */

class CharactersAllowedGlobally {
	private static Set<Character> allowed = initializeAllowed();

	private static Set<Character> initializeAllowed() {
		Set<Character> allowed = new HashSet<>();

		for (char c = 'a'; c <= 'z'; c++) {
			allowed.add(Character.valueOf(c));
		}

		for (char c = 'A'; c <= 'Z'; c++) {
			allowed.add(Character.valueOf(c));
		}

		for (char c = '0'; c <= '9'; c++) {
			allowed.add(Character.valueOf(c));
		}

		return Collections.unmodifiableSet(allowed);
	}

	public static Set<Character> get() {
		return allowed;
	}

}
