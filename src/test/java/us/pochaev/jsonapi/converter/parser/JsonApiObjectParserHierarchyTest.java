package us.pochaev.jsonapi.converter.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;


public class JsonApiObjectParserHierarchyTest {
	static final String TYPE_CHILD = "child";

	@JsonApiObject
	class Parent {
		@JsonApiId public Object id;
	}

	@JsonApiObject(TYPE_CHILD)
	class Child extends Parent{

	}

	class NotJsonApiChild extends Parent {
	}

	private JsonApiObjectParser jsonApiObjectParser;

	@BeforeEach
	public void beforeEach() {
		jsonApiObjectParser = new JsonApiObjectParser();
	}

	@Test
	@DisplayName("When @JsonApiObject parent and @JsonApiObject child then child type")
	public void whenAnnotatedChildClassThenConvertAsChildType() throws Exception {
		Child obj = new Child();
		obj.id = "unique";

		String type = jsonApiObjectParser.parseType(obj);

		assertEquals(TYPE_CHILD, type);
	}

	@Test
	@DisplayName("When @JsonApiObject parent and NOT @JsonApiObject child then exception")
	public void whenNotJsonApiObjectChildThenException() {
		Object obj = new NotJsonApiChild();
		Exception e = assertThrows(IllegalStateException.class, () ->
			jsonApiObjectParser.parseType(obj));
		assertEquals("Class must be annotated with @JsonApiObject", e.getMessage());
	}
}
