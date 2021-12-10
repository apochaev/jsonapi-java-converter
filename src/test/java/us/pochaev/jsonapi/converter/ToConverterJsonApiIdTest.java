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

public class ToConverterJsonApiIdTest {

	@JsonApiObject("NoJsonApiIdObject")
	class NoJsonApiIdObject {
	}

	@JsonApiObject("TwoJsonApiIdsObject")
	class TwoJsonApiIdsObject {
		@JsonApiId
		Object id1;

		@JsonApiId
		Object id2;
	}

	@JsonApiObject("TwoJsonapiIdsParentObject")
	class TwoJsonApiIdsParentObject extends JsonApiResourceObject {
		@JsonApiId
		private Object id;
	}

	@Test
	@DisplayName("When no @JsonApiId then exception")
	public void whenNoJsonApiIdThenException() {
		Object obj = new NoJsonApiIdObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class hierarchy must have a field annotated with @JsonApiId", e.getMessage());
	}

	@Test
	@DisplayName("When more then one @JsonApiId in class then exception")
	public void whenMoreThenOneJsonApiIdInClassThenException() {
		Object obj = new TwoJsonApiIdsObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class hierarchy must have a single field annotated with @JsonApiId", e.getMessage());
	}

	@Test
	@DisplayName("When more then one @JsonApiId in class hierarchy then exception")
	public void whenMoreThenOneJsonApiIdInHierarchyThenException() {
		Object obj = new TwoJsonApiIdsParentObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class hierarchy must have a single field annotated with @JsonApiId", e.getMessage());
	}

	@Test
	@DisplayName("When @JsonApiObject with null @JsonApiId then convert")
	public void whenJsonApiObjectwithNullJsonApiIdThenSuccess() throws Exception {
		Object obj = new JsonApiResourceObject();

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertNull(json.getString("id"));
		assertEquals("JsonApiResourceObject", json.getString("type"));
	}

	@Test
	@DisplayName("When @JsonApiObject with non null @JsonApiId then convert")
	public void whenJsonApiObjectWithNonNullJsonApiIdThenSuccess() throws Exception {
		UUID id = UUID.randomUUID();
		Object obj = new JsonApiResourceObject(id);

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals(id.toString(), json.getString("id"));
		assertEquals("JsonApiResourceObject", json.getString("type"));
	}
}
