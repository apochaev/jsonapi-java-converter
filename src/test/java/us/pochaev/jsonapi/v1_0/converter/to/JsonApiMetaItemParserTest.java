package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.None;
import us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.PublicField;



public class JsonApiMetaItemParserTest {

	@Test @DisplayName("WHEN none THEN empty map")
	public void whenNone() {
		Object obj = new None();

		Map<String, Object> metaItems = JsonApiMetaItemParser.parse(obj);
		assertTrue(metaItems.isEmpty());
	}

	@Test @DisplayName("WHEN public field THEN meta item")
	public void whenPublicFieldThenAttribute() {
		PublicField obj = new PublicField();
		obj.metaItem1 = "metaItem1";
		obj.metaItem2 = "metaItem2";

		Map<String, Object> metaItems = JsonApiMetaItemParser.parse(obj);
		assertEquals(1, metaItems.size());
		assertEquals("metaItem1", metaItems.get("metaItem1"));
	}


	// TODO check null value of an metaItem
	// TODO check renamed attribute (field, getter, conflicting)
}
