package us.pochaev.jsonapi.v1_0.converter.to.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonApiSpecificationExceptionTest {

	@Test @DisplayName("WHEN created without anchor THEN specUrl set to deafult value")
	void test1() {
		String message = "message";
		JsonApiSpecificationException ex = new JsonApiSpecificationException(message);
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationException.URL_JSONAPI_SPEC_V_1_0, ex.getSpecUrl());
	}

	@Test @DisplayName("WHEN created with null anchor THEN specUrl set to deafult value")
	void test2() {
		String message = "message";
		JsonApiSpecificationException ex = new JsonApiSpecificationException(message, null);
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationException.URL_JSONAPI_SPEC_V_1_0, ex.getSpecUrl());
	}

	@Test @DisplayName("WHEN created with blank anchor THEN specUrl set to deafult value")
	void test3() {
		String message = "message";
		JsonApiSpecificationException ex = new JsonApiSpecificationException(message, "  ");
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationException.URL_JSONAPI_SPEC_V_1_0, ex.getSpecUrl());
	}

	@Test @DisplayName("WHEN created with non-blank anchor THEN specUrl includes the anchor")
	void test4() {
		String message = "message";
		String anchor = "anchor";
		JsonApiSpecificationException ex = new JsonApiSpecificationException(message, anchor);
		assertEquals(message, ex.getMessage());
		assertEquals(JsonApiSpecificationException.URL_JSONAPI_SPEC_V_1_0 + "#" + anchor, ex.getSpecUrl());
	}
}
