package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.test_classes_attribute.None;
import us.pochaev.jsonapi.v1_0.converter.to.test_classes_attribute.PublicField;


public class JsonApiAttributeParserTest {

	@Test @DisplayName("WHEN none THEN empty map")
	public void whenNone() {
		Object obj = new None();

		Map<String, Object> attributes = JsonApiAttributeParser.parse(obj);
		assertTrue(attributes.isEmpty());
	}

	@Test @DisplayName("WHEN public field THEN attribute")
	public void whenPublicFieldThenAttribute() {
		Object obj = new PublicField();

		Map<String, Object> attributes = JsonApiAttributeParser.parse(obj);
		assertEquals(2, attributes.size());
		assertEquals("attributeValue1", attributes.get("attribute1"));
	}
	// TODO check null value of an attruibute
	// TODO check renamed attribute (field, getter, conflicting)
}
