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


public class ToConverterJsonApiObjectTest {
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


	@Test
	@DisplayName("When null object then exception")
	public void whenNullObjectThenException() {
		Object obj = null;
		assertThrows(NullPointerException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
	}

	@Test
	@DisplayName("When NOT @JsonApiObject then exception")
	public void whenNotJsonApiObjectThenException() {
		Object obj = new NotJsonApiObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class must be annotated with @JsonApiObject", e.getMessage());
	}

	@Test
	@DisplayName("When blank value then exception")
	public void whenBlankValueThenException() {
		Object obj = new BlankType();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("@JsonApiObject value may not be blank", e.getMessage());
	}

	@Test
	@DisplayName("When default value then simple name type")
	public void whenDefaultValueThenSimplenameType() {
		DefaultType obj = new DefaultType();
		obj.id = "unique";

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals(obj.id, json.getString("id"));
		assertEquals(DefaultType.class.getSimpleName(), json.getString("type"));
	}

	@Test
	@DisplayName("When empty value then simple name type")
	public void whenEmptyVaueThenSimpleNameType() {
		EmptyType obj = new EmptyType();
		obj.id = "unique";

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals(obj.id, json.getString("id"));
		assertEquals(EmptyType.class.getSimpleName(), json.getString("type"));
	}


	@Test
	@DisplayName("When custom value then custom type")
	public void whenJsonApiObjectWithNonNullJsonApiIdThenSuccess() throws Exception {
		CustomType obj = new CustomType();
		obj.id = "id";

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals(obj.id, json.getString("id"));
		assertEquals(TYPE_CUSTOM, json.getString("type"));
	}
}
