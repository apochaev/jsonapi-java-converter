package us.pochaev.jsonapi.converter;

import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.hasNoJsonPath;
import static com.jayway.jsonpath.matchers.JsonPathMatchers.isJson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.restassured.path.json.JsonPath;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.converter.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiObject.UnmarkedFields;

@SuppressWarnings("unused")
public class ToConverterIncludeAllPrivateTest {
	@JsonApiObject(value = "includeAllPrivateParent", includeUnmarkedFields = UnmarkedFields.All )
	class IncudeAllPrivateParent {
		@JsonApiId
		private String id = "privateParentId";
		private String privateField = "privateParentField";
		@JsonApiIgnore
		private String privateFieldIgnored = "privateParentFieldIgnored";
	}


	@JsonApiObject(value = "IncudeAllPrivateChild", includeUnmarkedFields = UnmarkedFields.All )
	class IncudeAllPrivateChild extends IncudeAllPrivateParent {
		private String privateChildField = "privateChildField";
		@JsonApiIgnore
		private String privateChildFieldIgnored = "privateChildFieldIgnored";
	}

	@Test
	@DisplayName("When include all and non annotated private field and then included")
	public void includeAllNonAnotatedPrivateField() {
		IncudeAllPrivateChild obj = new IncudeAllPrivateChild();

		String jsonString = JsonApiConverter.toJsonApiString(obj);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));
		assertThat(jsonString, hasJsonPath("attributes.privateParentField"));
		assertThat(jsonString, hasNoJsonPath("attributes.privateParentFieldIgnored"));
		assertThat(jsonString, hasJsonPath("attributes.privateChildField"));
		assertThat(jsonString, hasNoJsonPath("attributes.privateChildFieldIgnored"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals("includeAllPrivateId", json.getString("id"));
		assertEquals("JsonApiResourceObject", json.getString("type"));
		assertEquals("privateField", json.getString("attributes.privateField"));
	}
}
