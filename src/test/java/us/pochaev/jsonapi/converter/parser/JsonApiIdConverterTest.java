package us.pochaev.jsonapi.converter.parser;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;

public class JsonApiIdConverterTest {

	@JsonApiObject
	class NoJsonApiIdObject {
	}

	@JsonApiObject
	class PrivateNoGetterJsonApiIdObject {
		@JsonApiId private Object id;
	}

/*
	@JsonApiObject
	class NoPublicJsonApiIdIgnoreObject {
		@JsonApiId @JsonApiIgnore
		Object id;
	}


	@JsonApiObject
	class NoPublicJsonApiIdIgnoreObject {
		@JsonApiId @JsonApiIgnore
		Object id;
	}

	@JsonApiObject("JsonApiIdAttributeObject")
	class JsonApiIdAttributeObject {
		@JsonApiId @JsonApiAttribute
		Object id;
	}

	@JsonApiObject("JsonApiIdAttributeIgnoreObject")
	class JsonApiIdAttributeIgnoreObject {
		@JsonApiId @JsonApiAttribute @JsonApiIgnore
		Object id;
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
*/
	/*
	@Test
	@DisplayName("When no @JsonApiId then exception")
	public void whenNoJsonApiIdThenException() {
		Object obj = new NoJsonApiIdObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Class hierarchy must have a field annotated with @JsonApiId", e.getMessage());
	}

	@Test
	@DisplayName("When private @JsonApiId without public getter then exception")
	public void whenPrivateNoGetterJsonApiIdThenException() {
		Object obj = new PrivateNoGetterJsonApiIdObject();
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
	@DisplayName("When @JsonApiId and @JsonApiIgnore then exception")
	public void whenJsonApiIdAndJsonApiIgnoreThenException() {
		Object obj = new JsonApiIdIgnoreObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Field annotated with @JsonApiId may not be annotated with @JsonApiIgnore", e.getMessage());
	}

	@Test
	@DisplayName("When @JsonApiId and @JsonApiAttribute then exception")
	public void whenJsonApiIdAndJsonApiAttributeThenException() {
		Object obj = new JsonApiIdAttributeObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Field annotated with @JsonApiId may not be annotated with @JsonApiAttribute", e.getMessage());
	}

	@Test
	@DisplayName("When @JsonApiId and @JsonApiAttribute and @JsonApiIgnore then exception")
	public void whenJsonApiIdAndJsonApiAttributeAndJsonApiIgnoreThenException() {
		Object obj = new JsonApiIdAttributeIgnoreObject();
		Exception e = assertThrows(IllegalArgumentException.class, () ->
			JsonApiConverter.toJsonApiString(obj));
		assertEquals("Field annotated with @JsonApiId may not be annotated with @JsonApiAttribute", e.getMessage());
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
	*/
}
