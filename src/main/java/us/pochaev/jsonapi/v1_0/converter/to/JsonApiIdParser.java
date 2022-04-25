package us.pochaev.jsonapi.v1_0.converter.to;



import java.util.Collection;

import us.pochaev.jsonapi.reflection.value.ValueDescriptor;
import us.pochaev.jsonapi.reflection.value.ValueUtils;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;

class JsonApiIdParser {

	/**
	 * Returns value of JSON API Id
	 *
	 * Inspects all public fields and methods to find one annotated with {@link JsonApiId}
	 *
	 * @param obj JsonApiObject
	 * @return
	 * TODO document
	 */
	public static String parse(Object obj) {

		Collection<ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(JsonApiId.class, obj).values();

		switch (valueDescriptors.size()) {
			case 1: return getValue(valueDescriptors.iterator().next(), obj);
			case 0: throw mustHaveOne(obj.getClass());
			default : throw mustHaveSingle(obj.getClass());
		}
	}

	private static String getValue(ValueDescriptor vd, Object obj) {
		Object value = vd.getValue(obj);
		if (value != null) {
			return value.toString();
		}
		return null;
	}

	private static RuntimeException mustHaveOne(Class<? extends Object> cls) {
		return new IllegalStateException(
				cls.getCanonicalName() + " class hierarchy must have an accessible property annotated with @" + JsonApiId.class.getSimpleName());
	}

	private static IllegalStateException mustHaveSingle(Class<? extends Object> cls) {
		return new IllegalStateException(
				cls.getCanonicalName() + " class hierarchy must have a single accessible property annotated with @" + JsonApiId.class.getSimpleName());
	}
}
