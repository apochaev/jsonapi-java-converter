package us.pochaev.jsonapi.reflection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValueUtilsTest {


	@Test @DisplayName("WHEN capitalize camelCase THEN CamelCase")
	void testCapitalizeCamelCase() {
		String actual = ValueUtils.capitalize("camelCase");
		assertEquals("CamelCase", actual);
	}
}
