package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiParsingException;


public class JsonApiObjectParserTest {
	static final String TYPE_VALID = "valid_type";
	static final String TYPE_INVALID = "invalid$type";

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

	@JsonApiObject(TYPE_VALID)
	class ValidType {
		@JsonApiId public Object id;
	}

	@JsonApiObject(TYPE_INVALID)
	class InvalidType {
		@JsonApiId public Object id;
	}


	@BeforeEach
	public void beforeEach() {
	}


	@Test @DisplayName("WHEN null object THEN exception")
	public void whenNullObjectThenException() {
		Object obj = null;
		assertThrows(NullPointerException.class, () ->
			JsonApiObjectParser.parseType(obj));
	}

	@Test @DisplayName("WHEN NOT @JsonApiObject THEN exception")
	public void whenNotJsonApiObjectThenException() {
		Object obj = new NotJsonApiObject();
		Exception e = assertThrows(JsonApiParsingException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals(obj.getClass().getCanonicalName() + " must be annotated with @JsonApiObject.",
				e.getMessage());
	}

	@Test @DisplayName("WHEN empty value THEN exception")
	public void whenEmptyVaueThenSimpleNameType() {
		EmptyType obj = new EmptyType();
		obj.id = "unique";

		Exception e = assertThrows(JsonApiParsingException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals(obj.getClass().getCanonicalName() + " @JsonApiObject value must be valid.",
				e.getMessage());
		assertEquals("Member names MUST contain at least one character.",
				e.getCause().getMessage());
	}

	@Test @DisplayName("WHEN blank value THEN exception")
	public void whenBlankValueThenException() {
		Object obj = new BlankType();
		Exception e = assertThrows(JsonApiParsingException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals(obj.getClass().getCanonicalName() + " @JsonApiObject value must be valid.",
				e.getMessage());
		assertEquals("Member names MUST start and end with a “globally allowed character”.",
				e.getCause().getMessage());
	}

	@Test @DisplayName("WHEN value starts with a space THEN exception")
	public void whenValueStartsSpaceThenException() {
		Object obj = new StartsBlankType();
		Exception e = assertThrows(JsonApiParsingException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals(obj.getClass().getCanonicalName() + " @JsonApiObject value must be valid.",
				e.getMessage());
		assertEquals("Member names MUST start and end with a “globally allowed character”.",
				e.getCause().getMessage());
	}

	@Test @DisplayName("WHEN value ends with a space THEN exception")
	public void whenValueEndsSpaceThenException() {
		Object obj = new EndsBlankType();
		Exception e = assertThrows(JsonApiParsingException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals(obj.getClass().getCanonicalName() + " @JsonApiObject value must be valid.",
				e.getMessage());
		assertEquals("Member names MUST start and end with a “globally allowed character”.",
				e.getCause().getMessage());
	}

	@Test @DisplayName("WHEN default value THEN type equals simple name of the class")
	public void whenDefaultValueThenSimplenameType() {
		DefaultType obj = new DefaultType();
		obj.id = "unique";

		String type = JsonApiObjectParser.parseType(obj);

		assertEquals(DefaultType.class.getSimpleName(), type);
	}

	@Test @DisplayName("WHEN valid value THEN type equals value")
	public void whenJsonApiObjectWithNonNullJsonApiIdThenSuccess() throws Exception {
		ValidType obj = new ValidType();
		obj.id = "id";

		String type = JsonApiObjectParser.parseType(obj);

		assertEquals(TYPE_VALID, type);
	}

	@Test @DisplayName("WHEN invalid value THEN exception")
	public void whenInvalidValueThenException() {
		Object obj = new InvalidType();
		Exception e = assertThrows(JsonApiParsingException.class, () ->
			JsonApiObjectParser.parseType(obj));
		assertEquals(obj.getClass().getCanonicalName() + " @JsonApiObject value must be valid.",
				e.getMessage());
		assertEquals("Member names MUST contain only the allowed characters.",
				e.getCause().getMessage());
	}
}
