package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.id_parser.Constants;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.None;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.ParentPublicField;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PrivateAnnotatedField;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PrivateAnnotatedFieldPrivateGetter;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PrivateAnnotatedFieldPublicGetter;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PublicAnnotatedFieldPrivateGetter;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.PublicField;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.TwoAnnotatedFields;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.TwoAnnotatedMethods;
import us.pochaev.jsonapi_wip.converter.parser.test.id.ProtectedAnnotatedFieldPublicGetter;

class JsonApiIdParserTest {
	//TODO make sure lomboc @Data / @Value objects work.
	//TODO extract ReflectionUtils

	@Test @DisplayName("WHEN none THEN exception")
	public void whenNone() {
		Object obj = new None();

		IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
		assertEquals(obj.getClass().getCanonicalName() + " class hierarchy must have an accessible property annotated with @JsonApiId",
				ex.getMessage());
	}

	@Test @DisplayName("WHEN more then one field THEN exception")
	public void whenMoreThenOneAnnotatedField() {
		Object obj = new TwoAnnotatedFields();

		IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
		assertEquals(obj.getClass().getCanonicalName() + " class hierarchy must have a single accessible property annotated with @JsonApiId",
				ex.getMessage());
	}

	@Test @DisplayName("WHEN more then one method THEN exception")
	public void whenMoreThenOneAnnotatedMethod() {
		Object obj = new TwoAnnotatedMethods();

		IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
		assertEquals(obj.getClass().getCanonicalName() + " class hierarchy must have a single accessible property annotated with @JsonApiId",
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
	@DisplayName("WHEN not accessible annotated field not accessible getter THEN exception")
	public void whenNotAccessibleAnnotatedFieldNotAccessibleGetterThenException() {
		Object obj = new PrivateAnnotatedFieldPrivateGetter();

		IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
			JsonApiIdParser.parse(obj));
		assertEquals(obj.getClass().getCanonicalName() + "#id field annotated with @JsonApiId must be accessible.",
				ex.getMessage());
	}

	@Test
	@DisplayName("WHEN accessible annotated field not accessible getter THEN return field value")
	public void whenAccessibleAnnotatedFieldNotAccessibleGetterThenException() {
		Object obj = new PublicAnnotatedFieldPrivateGetter();

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
