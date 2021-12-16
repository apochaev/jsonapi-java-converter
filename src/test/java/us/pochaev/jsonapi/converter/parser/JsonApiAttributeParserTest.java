package us.pochaev.jsonapi.converter.parser;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;

public class JsonApiAttributeParserTest {
	private static final Object ID = "id";

	@JsonApiObject class ResourceIdentifierObject {
		@JsonApiId public Object id = ID;
	}

	@Test
	@DisplayName("When resource identifier object then id only")
	void whenResourceIdentifierObjectThenIdOnly() {
		Object obj = new ResourceIdentifierObject();
		
		Map<String, AttributeData> attributes = JsonApiAttributeParser.findAttributes(obj);
	}

}
