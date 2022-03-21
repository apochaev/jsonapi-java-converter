package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiSpecificationException;


/**
 * Validates member name conforms to <a href="https://jsonapi.org/format/#document-member-names">specification</a>
 *
 * @author apochaev
 *
 */
class MemberNameValidatorTest {

	@Test @DisplayName("MUST contain at least one character")
	void mustContainAtLeastOneChar() {
		String name = "";
		JsonApiSpecificationException ex = assertThrows(JsonApiSpecificationException.class, () ->
			MemberNameValidator.validate(name));
		assertEquals("Member names MUST contain at least one character", ex.getMessage());
		assertEquals("https://jsonapi.org/format/#document-member-names", ex.getSpecUrl());
	}


	@Test
	@DisplayName("Performance test") //TODO document findings and remove
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
