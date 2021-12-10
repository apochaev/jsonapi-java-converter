package us.pochaev.jsonapi.converter;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.path.json.JsonPath;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.testclasses.JsonApiResourceObject;

public class ToConverterJsonApiObjectTest {

	class NotJsonApiObject {
	}

	@JsonApiObject("")
	class EmptyType {
		@JsonApiId
		private Object id;
	}

	@JsonApiObject(" ")
	class BlankType {
		@JsonApiId
		private Object id;
	}

	@JsonApiObject("AnnotatedChild")
	class AnnotatedChild extends JsonApiResourceObject {
	}

	class NotAnnotatedChild extends JsonApiResourceObject {
	}


	@Test
	@DisplayName("When null then exception")
	public void whenNullThenException() {
		Object obj = null;
		assertThrows(NullPointerException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
	}

	@Test
	@DisplayName("When not @JsonApiObject then exception")
	public void whenNotJsonApiObjectThenException() {
		Object obj = new NotJsonApiObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class must be annotated with @JsonObject", e.getMessage());
	}

	@Test
	@DisplayName("When minimal @JsonApiObject then convert")
	public void whenJsonApiObjectWithNonNullJsonApiIdThenSuccess() throws Exception {
		String id = UUID.randomUUID().toString();
		Object obj = new JsonApiResourceObject(id);

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals(id, json.getString("id"));
		assertEquals("JsonApiResourceObject", json.getString("type"));
	}

	@Test
	@DisplayName("When empty type then exception")
	public void whenEmptyTypeThenException() {
		Object obj = new EmptyType();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("@JsonObject value may not be blank", e.getMessage());
	}

	@Test
	@DisplayName("When blank type then exception")
	public void whenBlankTypeThenException() {
		Object obj = new BlankType();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("@JsonObject value may not be blank", e.getMessage());
	}

	@Test
	@DisplayName("When annotated child and parent classes then convert as child type")
	public void whenAnnotatedChildClassThenConvertAsChildType() throws Exception {
		Object obj = new AnnotatedChild();

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertNull(json.getString("id"));
		assertEquals("AnnotatedChild", json.getString("type"));
	}

	@Test
	@DisplayName("When not annotated child class then exception")
	public void whenNotAnnotatedChildClassThenConvertAsParentType() throws Exception {
		Object obj = new NotAnnotatedChild();

		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class must be annotated with @JsonObject", e.getMessage());
	}
}
