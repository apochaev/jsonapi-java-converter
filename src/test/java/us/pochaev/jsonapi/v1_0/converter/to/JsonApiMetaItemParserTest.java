package us.pochaev.jsonapi.v1_0.converter.to;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.All.META_ITEM_ANNOTATED_RENAMED_VALUE;
import static us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.All.META_ITEM_ANNOTATED_VALUE;
import static us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.All.META_ITEM_GETTER_RENAMED_VALUE;
import static us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.All.META_ITEM_GETTER_VALUE;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.All;
import us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta.None;



public class JsonApiMetaItemParserTest {

	@Test @DisplayName("WHEN none THEN empty map")
	public void whenNone() {
		Object obj = new None();

		Map<String, Object> metaItems = JsonApiMetaItemParser.parse(obj);
		assertTrue(metaItems.isEmpty());
	}

	@Test @DisplayName("WHEN annotated values THEN meta items")
	public void whenPublicFieldThenAttribute() {
		All obj = new All();
		Map<String, Object> metaItems = JsonApiMetaItemParser.parse(obj);
		assertEquals(4, metaItems.size());
		assertEquals(META_ITEM_ANNOTATED_VALUE, metaItems.get("metaItemAnnotated"));
		assertFalse(metaItems.containsKey("notAnnotated"));
		assertFalse(metaItems.containsKey("metaItemAnnotatedIgnored"));
		assertEquals(META_ITEM_ANNOTATED_RENAMED_VALUE, metaItems.get("metaItemAnnotatedRenamed"));
		assertFalse(metaItems.containsKey("someProperty"));
		assertEquals(META_ITEM_GETTER_VALUE, metaItems.get("metaItemGetter"));
		assertEquals(META_ITEM_GETTER_RENAMED_VALUE, metaItems.get("metaItemGetterRenamed"));
		assertFalse(metaItems.containsKey("metaItemGetterIgnored"));
	}


	// TODO check null value of an metaItem
	// TODO check renamed attribute (field, getter, conflicting)
}
