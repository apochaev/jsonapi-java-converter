package us.pochaev.jsonapi.reflection.property;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PropertyNameUtilsTest {

	@Test @DisplayName("WHEN capitalize camelCase THEN CamelCase")
	void testCapitalizeCamelCase() {
		String actual = PropertyNameUtils.capitalize("camelCase");
		assertEquals("CamelCase", actual);
	}

	@Test @DisplayName("WHEN decapitalize Property THEN property")
	void testDecapitalizeProperty() {
		String actual = PropertyNameUtils.decapitalize("Property");
		assertEquals("property", actual);
	}

	@Test @DisplayName("WHEN getProperty THEN getOptionalPropertyName is present")
	void test1() {
		String methodName = "getProperty";
		Optional<String> optionalPropertyName = PropertyNameUtils.getOptionalPropertyName(methodName);
		assertTrue(optionalPropertyName.isPresent());
		assertEquals("property", optionalPropertyName.get());
	}

	@Test @DisplayName("WHEN getproperty THEN getOptionalPropertyName is NOT present")
	void test2() {
		String methodName = "getproperty";
		Optional<String> optionalPropertyName = PropertyNameUtils.getOptionalPropertyName(methodName);
		assertFalse(optionalPropertyName.isPresent());
	}

	@Test @DisplayName("WHEN isProperty THEN getOptionalPropertyName is present")
	void test3() {
		String methodName = "isProperty";
		Optional<String> optionalPropertyName = PropertyNameUtils.getOptionalPropertyName(methodName);
		assertTrue(optionalPropertyName.isPresent());
		assertEquals("property", optionalPropertyName.get());
	}

	@Test @DisplayName("WHEN isproperty THEN getOptionalPropertyName is NOT present")
	void test4() {
		String methodName = "isproperty";
		Optional<String> optionalPropertyName = PropertyNameUtils.getOptionalPropertyName(methodName);
		assertFalse(optionalPropertyName.isPresent());
	}

	@Test @DisplayName("WHEN get THEN getOptionalPropertyName is NOT present")
	void test5() {
		String methodName = "get";
		Optional<String> optionalPropertyName = PropertyNameUtils.getOptionalPropertyName(methodName);
		assertFalse(optionalPropertyName.isPresent());
	}

	@Test @DisplayName("WHEN get THEN getOptionalPropertyName is NOT present")
	void test6() {
		String methodName = "is";
		Optional<String> optionalPropertyName = PropertyNameUtils.getOptionalPropertyName(methodName);
		assertFalse(optionalPropertyName.isPresent());
	}
}
