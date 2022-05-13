package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiParsingException;


public class JsonApiObjectParserClassHierarchyTest {
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

	@Test @DisplayName("WHEN @JsonApiObject child and @JsonApiObject parent THEN child type")
	public void whenAnnotatedChildClassThenConvertAsChildType() throws Exception {
		Child obj = new Child();
		obj.id = "unique";

		String type = JsonApiObjectParser.parse(obj);

		assertEquals(TYPE_CHILD, type);
	}

	@Test @DisplayName("WHEN NOT @JsonApiObject child and @JsonApiObject parent THEN exception")
	public void whenNotJsonApiObjectChildThenException() {
		Object obj = new NotJsonApiChild();
		Exception e = assertThrows(JsonApiParsingException.class, () ->
			JsonApiObjectParser.parse(obj));
		assertEquals(obj.getClass().getCanonicalName() + " must be annotated with @JsonApiObject.",
				e.getMessage());
	}
}
