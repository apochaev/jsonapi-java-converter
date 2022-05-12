package us.pochaev.jsonapi.v1_0.converter.to;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import us.pochaev.jsonapi.reflection.value.ValueDescriptor;
import us.pochaev.jsonapi.reflection.value.ValueUtils;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiMetaItem;

class JsonApiMetaItemParser {

	/**
	 * Returns map of JSON API attribute to attribute value.
	 *
	 * Inspects all public fields and getters to find ones not annotated with {@link JsonApIgnore}
	 *
	 * @param obj JsonApiObject
	 * @return
	 * TODO document
	 */
	public static Map<String, Object> parse(Object obj) {
		HashMap<String, Object> metaItems = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		Map<String, ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(
				new Class[]{JsonApiMetaItem.class},
				new Class[]{JsonApiId.class, JsonApiIgnore.class},
				obj.getClass());

		for (String key : valueDescriptors.keySet()) {
			ValueDescriptor valueDescriptor = valueDescriptors.get(key);
			metaItems.put(
					getAttributeName(valueDescriptor, key),
					getValue(valueDescriptor,obj));
		}

		return metaItems;
	}

	protected static String getAttributeName(ValueDescriptor valueDescriptor, String defaultValue) {
		Optional<JsonApiAttribute> optionalAnnotation = valueDescriptor.getOptionalAnnotation(JsonApiAttribute.class);
		if (optionalAnnotation.isPresent()) {
			String customValue = optionalAnnotation.get().value();
			if (!JsonApiAttribute.DEFAULT_VALUE.equals(customValue)) {
				return customValue;
			}
		}
		return defaultValue;
	}

	private static Object getValue(ValueDescriptor vd, Object obj) {
		Object value = vd.getValue(obj);
		if (value != null) {
			return value.toString();
		}
		return null;
	}

}
