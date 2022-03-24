package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.id_parser.Constants;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.None;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.ParentPublicField;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PrivateAnnotatedField;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PrivateAnnotatedFieldPublicGetter;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PublicField;
import us.pochaev.jsonapi_wip.converter.parser.test.id.ProtectedAnnotatedFieldPublicGetter;

class JsonApiIdParserTest { //TODO make sure lomboc @Data / @Value objects work.

	@Test @DisplayName("WHEN none THEN exception")
	public void whenNone() {
		Object obj = new None();

		IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
		assertEquals(obj.getClass().getCanonicalName() + " class hierarchy must have a public property annotated with @JsonApiId",
				ex.getMessage());
	}

	@Test @DisplayName("WHEN annotated accessible field THEN return field value")
	public void whenAnnotatedAccessibleFieldThenReturnValue() {
		Object obj = new PublicField();

		String id = JsonApiIdParser.parse(obj);
		assertEquals(Constants.ID, id);
	}

	@Test @DisplayName("WHEN annotated accessible parent field THEN return parent field value")
	public void whenAnnotatedAccessibleParentFieldThenReturnParentValue() {
		Object obj = new ParentPublicField();

		String id = JsonApiIdParser.parse(obj);
		assertEquals(Constants.ID, id);
	}

	@Test
	@DisplayName("WHEN not accessible annotated field THEN exception")
	public void whenNotAccessibleAnnotatedFieldThenException() {
		Object obj = new PrivateAnnotatedField();

		IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
		assertEquals(obj.getClass().getCanonicalName() + "#id field annotated with @JsonApiId must be accessible.",
				ex.getMessage());
	}

	@Test
	@DisplayName("WHEN not accessible annotated field accessible getter THEN return field value")
	public void whenNotAccessibleAnnotatedFieldAccessibleGetterThenReturnFieldValue() {
		Object obj = new PrivateAnnotatedFieldPublicGetter();

		String id = JsonApiIdParser.parse(obj);
		assertEquals(Constants.ID, id);
	}





	@Test
	@DisplayName("WHEN protected annotated field with public getter THEN return field values")
	public void whenPrivateFieldPublicGetter() {
		Object obj = new ProtectedAnnotatedFieldPublicGetter();

		String id = JsonApiIdParser.parse(obj);
		assertEquals(Constants.ID, id);
	}

	//TODO boolean, Boolean, other primitive other non primitive

}
