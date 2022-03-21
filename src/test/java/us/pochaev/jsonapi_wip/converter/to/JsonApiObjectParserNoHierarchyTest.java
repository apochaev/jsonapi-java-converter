package us.pochaev.jsonapi_wip.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiId;
import us.pochaev.jsonapi_wip.converter.to.JsonApiObjectParser;


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

	@JsonApiObject(" type")
	class StartsBlankType {
	}

	@JsonApiObject("type ")
	class EndsBlankType {
	}

	@JsonApiObject(TYPE_CUSTOM)
	class CustomType {
		@JsonApiId public Object id;
	}

	@BeforeEach
	public void beforeEach() {
	}

	@Test
	@DisplayName("When null object then exception")
	public void whenNullObjectThenException() {
		Object obj = null;
		assertThrows(NullPointerException.class, () ->
			JsonApiObjectParser.parseType(obj));
	}

	@Test
	@DisplayName("When NOT @JsonApiObject then exception")
	public void whenNotJsonApiObjectThenException() {
		Object obj = new NotJsonApiObject();
		Exception e = assertThrows(IllegalStateException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals("Class must be annotated with @JsonApiObject", e.getMessage());
	}

	@Test
	@DisplayName("When blank value then exception")
	public void whenBlankValueThenException() {
		Object obj = new BlankType();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals("@JsonApiObject value may not be blank", e.getMessage());
	}

	@Test
	@DisplayName("When value starts with a space then exception")
	public void whenValueStartsSpaceThenException() {
		Object obj = new StartsBlankType();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals("@JsonApiObject value may not start or end with a space", e.getMessage());
	}

	@Test
	@DisplayName("When value ends with a space then exception")
	public void whenValueEndsSpaceThenException() {
		Object obj = new EndsBlankType();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals("@JsonApiObject value may not start or end with a space", e.getMessage());
	}

	@Test
	@DisplayName("When default value then simple name type")
	public void whenDefaultValueThenSimplenameType() {
		DefaultType obj = new DefaultType();
		obj.id = "unique";

		String type = JsonApiObjectParser.parseType(obj);

		assertEquals(DefaultType.class.getSimpleName(), type);
	}

	@Test
	@DisplayName("When empty value then simple name type")
	public void whenEmptyVaueThenSimpleNameType() {
		EmptyType obj = new EmptyType();
		obj.id = "unique";

		String type = JsonApiObjectParser.parseType(obj);

		assertEquals(EmptyType.class.getSimpleName(), type);
	}

	@Test
	@DisplayName("When custom value then custom type")
	public void whenJsonApiObjectWithNonNullJsonApiIdThenSuccess() throws Exception {
		CustomType obj = new CustomType();
		obj.id = "id";

		String type = JsonApiObjectParser.parseType(obj);

		assertEquals(TYPE_CUSTOM, type);
	}
}
