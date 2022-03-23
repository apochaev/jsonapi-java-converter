package us.pochaev.jsonapi_wip.converter.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.id_parser.Constants;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PrivateAnnotatedField;
import us.pochaev.jsonapi_wip.converter.parser.test.id.PrivateFieldPrivateGetterBoth;
import us.pochaev.jsonapi_wip.converter.parser.test.id.PrivateFieldPublicGetter;

public class JsonApiIdFieldFinderTest {



	@Test
	@DisplayName("When more then one then exception")
	public void whenMore() {
		Object obj = new PrivateFieldPrivateGetterBoth();

		IllegalStateException e = assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
		assertEquals("", e);
	}




	@Test
	@DisplayName("When private field then exception")
	public void whenPrivateField() {
		Object obj = new PrivateAnnotatedField();

		assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
	}


	@Test
	@DisplayName("When private field and public getter annotated then exception")
	public void whenPrivateFieldPublicGetterBoth() {
		Object obj = new PrivateFieldPublicGetter();

		String id = JsonApiIdParser.parse(obj);
		assertEquals(Constants.ID, id);
	}
}
