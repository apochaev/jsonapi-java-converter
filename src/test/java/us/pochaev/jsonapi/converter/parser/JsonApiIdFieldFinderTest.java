package us.pochaev.jsonapi.converter.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.converter.parser.test.id.Constants;
import us.pochaev.jsonapi.converter.parser.test.id.PrivateField;
import us.pochaev.jsonapi.converter.parser.test.id.PrivateFieldPublicGetter;
import us.pochaev.jsonapi.converter.parser.test.id.ProtectedField;
import us.pochaev.jsonapi.converter.parser.test.id.PublicField;

public class JsonApiIdFieldFinderTest {

	@Test
	@DisplayName("When public field then return field value")
	public void whenPublicFieldThenReturnValue() {
		Object obj = new PublicField();

		String id = JsonApiIdFieldFinder.find(obj);
		assertEquals(Constants.ID, id);
	}

	@Test
	@DisplayName("When protected field then exception")
	public void whenProtectedField() {
		Object obj = new ProtectedField();

		assertThrows(IllegalStateException.class, () ->
			JsonApiIdFieldFinder.find(obj));
	}

	@Test
	@DisplayName("When private field then exception")
	public void whenPrivateField() {
		Object obj = new PrivateField();

		assertThrows(IllegalStateException.class, () ->
			JsonApiIdFieldFinder.find(obj));
	}

	@Test
	@DisplayName("When private field with public getter then return field values")
	public void whenPrivateFieldPublicGetter() {
		Object obj = new PrivateFieldPublicGetter();

		String id = JsonApiIdFieldFinder.find(obj);
		assertEquals(Constants.ID, id);
	}
}
