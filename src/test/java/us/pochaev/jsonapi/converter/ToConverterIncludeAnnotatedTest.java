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
public class ToConverterIncludeAnnotatedTest {

	@JsonApiObject(value = "parent", includeUnmarkedFields = UnmarkedFields.All )
	class IncudeAllPrivateParent {
		@JsonApiId
		private String id = "privateParentId";
		private String privateParentField = "privateParentField";
		private String privateParentFieldToOverride = "privateParentFieldToOverride";
		@JsonApiIgnore
		private String privateParentFieldIgnored = "privateParentFieldIgnored";
	}

	@JsonApiObject(value = "child", includeUnmarkedFields = UnmarkedFields.All )
	class IncudeAllPrivateChild extends IncudeAllPrivateParent {
		private String privateChildField = "privateChildField";
		private String privateParentFieldToOverride = "privateParentFieldOverridden";
		@JsonApiIgnore
		private String privateChildFieldIgnored = "privateChildFieldIgnored";
	}

	@Test
	@DisplayName("When include all and non annotated private field and then included")
	public void includeAllNonAnotatedPrivateField() {
		IncudeAllPrivateChild obj = new IncudeAllPrivateChild();

		System.out.println(JsonUtils.toJsonString(obj));

		String jsonString = JsonApiConverter.toJsonApiString(obj);
		System.out.println(jsonString);

		assertThat(jsonString, isJson());
		assertThat(jsonString, hasJsonPath("id"));
		assertThat(jsonString, hasJsonPath("type"));
		assertThat(jsonString, hasJsonPath("attributes.privateParentField"));
		assertThat(jsonString, hasNoJsonPath("attributes.privateParentFieldIgnored"));
		assertThat(jsonString, hasJsonPath("attributes.privateChildField"));
		assertThat(jsonString, hasNoJsonPath("attributes.privateChildFieldIgnored"));

		JsonPath json = JsonPath.from(jsonString);

		assertEquals("privateParentId", json.getString("id"));
		assertEquals("IncudeAllPrivateChild", json.getString("type"));
		assertEquals("privateParentField", json.getString("attributes.privateParentField"));
		assertEquals("privateChildField", json.getString("attributes.privateChildField"));

	}
}
