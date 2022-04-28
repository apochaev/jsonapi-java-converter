package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
		PublicField obj = new PublicField();
		obj.attribute1 = "attributeValue1";
		obj.attribute2 = "attributeValue2";
		obj.attributeThree = "attributeValue3";

		Map<String, Object> attributes = JsonApiAttributeParser.parse(obj);
		assertEquals(3, attributes.size());
		assertEquals("attributeValue1", attributes.get("attribute1"));
		assertEquals("attributeValue2", attributes.get("attribute2"));
		assertEquals("attributeValue3", attributes.get("attribute3")); //renamed by the annotation
	}

	@Test @DisplayName("WHEN public field value is null THEN attribute value is null")
	public void whenPublicFieldNullThenAttributeNull() {
		PublicField obj = new PublicField();
		obj.attribute1 = null;

		Map<String, Object> attributes = JsonApiAttributeParser.parse(obj);
		assertEquals(1, attributes.size());
		assertNull(attributes.get("attribute1"));
	}
	// TODO check null value of an attruibute
	// TODO check renamed attribute (field, getter, conflicting)
}
