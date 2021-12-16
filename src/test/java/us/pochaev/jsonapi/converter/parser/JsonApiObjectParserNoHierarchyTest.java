package us.pochaev.jsonapi.converter.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;


public class JsonApiObjectParserNoHierarchyTest {
	static final String TYPE_CUSTOM = "custom";

	class NotJsonApiObject {
	}

	@JsonApiObject
	class DefaultType {
		@JsonApiId public Object id;
	}

	@JsonApiObject("")
	class EmptyType {
		@JsonApiId public Object id;
	}

	@JsonApiObject(" ")
	class BlankType {
	}

	@JsonApiObject(TYPE_CUSTOM)
	class CustomType {
		@JsonApiId public Object id;
	}

	private JsonApiObjectParser jsonApiObjectParser;

	@BeforeEach
	public void beforeEach() {
		jsonApiObjectParser = new JsonApiObjectParser();
	}

	@Test
	@DisplayName("When null object then exception")
	public void whenNullObjectThenException() {
		Object obj = null;
		assertThrows(NullPointerException.class, () ->
			jsonApiObjectParser.parseType(obj));
	}

	@Test
	@DisplayName("When NOT @JsonApiObject then exception")
	public void whenNotJsonApiObjectThenException() {
		Object obj = new NotJsonApiObject();
		Exception e = assertThrows(IllegalStateException.class, () ->
			jsonApiObjectParser.parseType(obj));
		assertEquals("Class must be annotated with @JsonApiObject", e.getMessage());
	}

	@Test
	@DisplayName("When blank value then exception")
	public void whenBlankValueThenException() {
		Object obj = new BlankType();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			jsonApiObjectParser.parseType(obj));
		assertEquals("@JsonApiObject value may not be blank", e.getMessage());
	}

	@Test
	@DisplayName("When default value then simple name type")
	public void whenDefaultValueThenSimplenameType() {
		DefaultType obj = new DefaultType();
		obj.id = "unique";

		String type = jsonApiObjectParser.parseType(obj);

		assertEquals(DefaultType.class.getSimpleName(), type);
	}

	@Test
	@DisplayName("When empty value then simple name type")
	public void whenEmptyVaueThenSimpleNameType() {
		EmptyType obj = new EmptyType();
		obj.id = "unique";

		String type = jsonApiObjectParser.parseType(obj);

		assertEquals(EmptyType.class.getSimpleName(), type);
	}

	@Test
	@DisplayName("When custom value then custom type")
	public void whenJsonApiObjectWithNonNullJsonApiIdThenSuccess() throws Exception {
		CustomType obj = new CustomType();
		obj.id = "id";

		String type = jsonApiObjectParser.parseType(obj);

		assertEquals(TYPE_CUSTOM, type);
	}
}
