package us.pochaev.jsonapi.v1_0.converter.to.membername;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiSpecificationViolation;

class ValidatorTest {

	@Test @DisplayName("WHEN contains all allowed characters THEN is returned")
	void test1() {
		StringBuilder sb = new StringBuilder();
			sb.append(getGloballyAllowedCharacter());

		for (Character c : CharactersAllowedGlobally.get()) {
			sb.append(String.valueOf(c));
		}

		for (Character c : CharactersAllowedInMiddleOnly.get()) {
			sb.append(String.valueOf(c));
		}

			sb.append(getGloballyAllowedCharacter());

		String name = sb.toString();
		String validName = Validator.validate(name);
		assertEquals(name, validName);
	}

	@Test @DisplayName("WHEN NOT contains at least one character THEN exception")
	void test2() {
		String name = "";
		JsonApiSpecificationViolation ex = assertThrows(JsonApiSpecificationViolation.class, () ->
			Validator.validate(name));
		assertEquals("Member names MUST contain at least one character.", ex.getMessage());
		assertEquals("https://jsonapi.org/format/1.0#document-member-names", ex.getSpecUrl());
	}

	@Test @DisplayName("WHEN starts or ends with a chracter NOT allowed in first or last place THEN exception")
	void test3() {
		for (Character c : CharactersAllowedInMiddleOnly.get()) {
				String startsWith = String.valueOf(c) + getGloballyAllowedCharacterAsString();
				assertThrowsMustStartAndEndWithGloballyAllowedCharacterException(startsWith);

				String endsWith = getGloballyAllowedCharacterAsString() + String.valueOf(c);
				assertThrowsMustStartAndEndWithGloballyAllowedCharacterException(endsWith);
		}
	}

	@Test @DisplayName("WHEN contains reserved character THEN exception")
	void test4() {
		for (Character c : ReservedCharacters.get()) {
				String name = getGloballyAllowedCharacterAsString() +
						String.valueOf(c) +
						getGloballyAllowedCharacterAsString();

				JsonApiSpecificationViolation ex = assertThrows(JsonApiSpecificationViolation.class, () ->
					Validator.validate(name));

				assertEquals("Member names MUST contain only the allowed characters.", ex.getMessage());
				assertEquals("https://jsonapi.org/format/1.0#document-member-names-allowed-characters", ex.getSpecUrl());
		}
	}

	@Test @DisplayName("WHEN contains invalid non-reserved character THEN exception")
	void test5() {
				String name = getGloballyAllowedCharacterAsString() +
						"Ж" +
						getGloballyAllowedCharacterAsString();

				JsonApiSpecificationViolation ex = assertThrows(JsonApiSpecificationViolation.class, () ->
					Validator.validate(name));

				assertEquals("Member names MUST contain only the allowed characters.", ex.getMessage());
				assertEquals("https://jsonapi.org/format/1.0#document-member-names-allowed-characters", ex.getSpecUrl());
	}



	private void assertThrowsMustStartAndEndWithGloballyAllowedCharacterException(String name) {
		JsonApiSpecificationViolation ex = assertThrows(JsonApiSpecificationViolation.class, () ->
			Validator.validate(name));
		assertEquals("Member names MUST start and end with a “globally allowed character”.", ex.getMessage());
		assertEquals("https://jsonapi.org/format/1.0#document-member-names-allowed-characters", ex.getSpecUrl());
	}

	private String getGloballyAllowedCharacterAsString() {
		return String.valueOf(getGloballyAllowedCharacter());
	}

	private Character getGloballyAllowedCharacter() {
		return CharactersAllowedGlobally.get().iterator().next();
	}


	@Disabled("Document findings and remove test") //TODO Document findings and remove test
	@Test @DisplayName("Performance test")
 	void test() {
		String test1 = RandomStringUtils.randomAlphabetic(20).toLowerCase();
		String test2 = RandomStringUtils.randomAlphabetic(19).toLowerCase() + "Ж";

		{
			boolean valid = false;
			long started = System.nanoTime();
			if (test1.matches(".*[a-z]")) {
				valid = true;
			}
			long finished = System.nanoTime();
			assertTrue(valid);
			System.out.println((finished - started) + "\t ns \t regexp: " + valid);
		}

		{
			boolean valid = false;
			long started = System.nanoTime();
			if (test2.matches(".*[a-z]")) {
				valid = true;
			}
			long finished = System.nanoTime();
			assertFalse(valid);
			System.out.println((finished - started) + "\t ns \t regexp: " + valid);
		}

		{
			long started = System.nanoTime();

			Set<Character> allowed = new HashSet<>();
			for (char charToCheck = 'a'; charToCheck <= 'z'; charToCheck++) {
				allowed.add(Character.valueOf(charToCheck));
			}

			boolean valid = true;

			for (char c : test1.toCharArray()) {
				if (! allowed.contains(Character.valueOf(c))) {
					valid = false;
				}
			}
			long finished = System.nanoTime();
			assertTrue(valid);
			System.out.println((finished - started) + "\t ns \t loop: " + valid);
		}

		{
			long started = System.nanoTime();

			Set<Character> allowed = new HashSet<>();
			for (char charToCheck = 'a'; charToCheck <= 'z'; charToCheck++) {
				allowed.add(Character.valueOf(charToCheck));
			}

			boolean valid = true;

			for (char c : test2.toCharArray()) {
				if (! allowed.contains(Character.valueOf(c))) {
					valid = false;
				}
			}
			long finished = System.nanoTime();
			assertFalse(valid);
			System.out.println((finished - started) + "\t ns \t loop: " + valid);
		}

	}

}
