package us.pochaev.jsonapi_wip.converter.parser;

import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

public class JsonApiAttributeParserTest {
	private static final Object ID = "id";

	@JsonApiObject class ResourceIdentifierObject {
		@JsonApiId public Object id = ID;
	}

	@Test
	@DisplayName("When resource identifier object then id only")
	@Disabled("WiP")
	void whenResourceIdentifierObjectThenIdOnly() {
		Object obj = new ResourceIdentifierObject();

		Map<String, AttributeData> attributes = JsonApiAttributeParser.findAttributes(obj);
	}

}
