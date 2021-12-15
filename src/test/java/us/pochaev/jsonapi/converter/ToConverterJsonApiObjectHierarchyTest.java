package us.pochaev.jsonapi.converter;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.path.json.JsonPath;
import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;


public class ToConverterJsonApiObjectHierarchyTest {
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

	@Test
	@DisplayName("When @JsonApiObject parent and @JsonApiObject child then child type")
	public void whenAnnotatedChildClassThenConvertAsChildType() throws Exception {
		Child child = new Child();
		child.id = "unique";

		String jsonString = JsonApiConverter.toJsonApiString(child);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals(child.id, json.getString("id"));
		assertEquals(TYPE_CHILD, json.getString("type"));
	}

	@Test
	@DisplayName("When @JsonApiObject parent and NOT @JsonApiObject child then exception")
	public void whenNotJsonApiObjectChildThenException() {
		Object obj = new NotJsonApiChild();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class must be annotated with @JsonApiObject", e.getMessage());
	}
}
