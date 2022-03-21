package us.pochaev.jsonapi.v1_0.converter.to.membername;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ReservedCharacters {
	private static Set<Character> reserved = initializeReserved();

	private static Set<Character> initializeReserved() {
		Set<Character> reserved = new HashSet<>();

		reserved.add("+".charAt(0));
		reserved.add(",".charAt(0));
		reserved.add(".".charAt(0));
		reserved.add("[".charAt(0));
		reserved.add("]".charAt(0));
		reserved.add("!".charAt(0));
		reserved.add("”".charAt(0));
		reserved.add("#".charAt(0));
		reserved.add("$".charAt(0));
		reserved.add("%".charAt(0));
		reserved.add("&".charAt(0));
		reserved.add("’".charAt(0));
		reserved.add("(".charAt(0));
		reserved.add(")".charAt(0));
		reserved.add("*".charAt(0));
		reserved.add("/".charAt(0));
		reserved.add(":".charAt(0));
		reserved.add(";".charAt(0));
		reserved.add("<".charAt(0));
		reserved.add("=".charAt(0));
		reserved.add(">".charAt(0));
		reserved.add("?".charAt(0));
		reserved.add("@".charAt(0));
		reserved.add("\\".charAt(0));
		reserved.add("^".charAt(0));
		reserved.add("`".charAt(0));
		reserved.add("{".charAt(0));
		reserved.add("|".charAt(0));
		reserved.add("}".charAt(0));
		reserved.add("~".charAt(0));
		reserved.add((char)Integer.parseInt("7F",16));   //DELETE
		for (int i = 0; i <= Integer.parseInt("1F",16); i++) {// (C0 Controls)
			reserved.add((char)i);
		}

		return Collections.unmodifiableSet(reserved);
	}

	public static Set<Character> get() {
		return reserved;
	}

}
