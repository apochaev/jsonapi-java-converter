package us.pochaev.jsonapi.v1_0.converter.to.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonApiSpecificationViolationTest {

	@Test @DisplayName("WHEN created without anchor THEN specUrl set to deafult value")
	void test1() {
		String message = "message";
		JsonApiSpecificationViolation ex = new JsonApiSpecificationViolation(message);
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationViolation.URL_JSONAPI_SPEC_V_1_0, ex.getSpecUrl());
	}

	@Test @DisplayName("WHEN created with null anchor THEN specUrl set to deafult value")
	void test2() {
		String message = "message";
		JsonApiSpecificationViolation ex = new JsonApiSpecificationViolation(message, null);
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationViolation.URL_JSONAPI_SPEC_V_1_0, ex.getSpecUrl());
	}

	@Test @DisplayName("WHEN created with blank anchor THEN specUrl set to deafult value")
	void test3() {
		String message = "message";
		JsonApiSpecificationViolation ex = new JsonApiSpecificationViolation(message, "  ");
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationViolation.URL_JSONAPI_SPEC_V_1_0, ex.getSpecUrl());
	}

	@Test @DisplayName("WHEN created with non-blank anchor THEN specUrl includes the anchor")
	void test4() {
		String message = "message";
		String anchor = "anchor";
		JsonApiSpecificationViolation ex = new JsonApiSpecificationViolation(message, anchor);
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationViolation.URL_JSONAPI_SPEC_V_1_0 + "#" + anchor, ex.getSpecUrl());
	}
}
