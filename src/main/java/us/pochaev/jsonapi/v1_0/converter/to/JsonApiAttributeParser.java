package us.pochaev.jsonapi.v1_0.converter.to;



import java.util.HashMap;
import java.util.Map;

import us.pochaev.jsonapi.reflection.value.ValueDescriptor;
import us.pochaev.jsonapi.reflection.value.ValueUtils;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiParsingException;

class JsonApiAttributeParser {

	/**
	 * Returns map of JSON API Id attribute to attribute value.
	 *
	 * Inspects all public fields and getters to find ones not annotated with {@link JsonApIgnore}
	 *
	 * @param obj JsonApiObject
	 * @return
	 * TODO document
	 */
	public static Map<String, Object> parse(Object obj) {

		Map<String, ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(
				null, new Class[] {JsonApiIgnore.class}, obj);
//
//		switch (valueDescriptors.size()) {
//			case 1: return getValue(valueDescriptors.iterator().next(), obj);
//			case 0: throw mustHaveOne(obj.getClass());
//			default : throw mustHaveSingle(obj.getClass());
//		}
		return new HashMap<String, Object>();
	}

	private static Object getValue(ValueDescriptor vd, Object obj) {
		Object value = vd.getValue(obj);
		if (value != null) {
			return value.toString();
		}
		return null;
	}

	private static JsonApiParsingException mustHaveOne(Class<? extends Object> cls) {
		return new JsonApiParsingException(
				cls.getCanonicalName() + " class hierarchy must have an accessible property annotated with @" + JsonApiId.class.getSimpleName());
	}

	private static JsonApiParsingException mustHaveSingle(Class<? extends Object> cls) {
		return new JsonApiParsingException(
				cls.getCanonicalName() + " class hierarchy must have a single accessible property annotated with @" + JsonApiId.class.getSimpleName());
	}
}
