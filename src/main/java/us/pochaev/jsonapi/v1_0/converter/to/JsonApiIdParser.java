package us.pochaev.jsonapi.v1_0.converter.to;



import java.util.Collection;

import us.pochaev.jsonapi.reflection.value.ValueDescriptor;
import us.pochaev.jsonapi.reflection.value.ValueUtils;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiParsingException;

class JsonApiIdParser {

	/**
	 * Returns value of JSON API Id
	 *
	 * Inspects all public fields and getters to find one annotated with {@link JsonApiId}
	 *
	 * @param obj Instance of a {@link JsonApiObject} annotated class.
	 * @return String value of the class member annotated with {@link JsonApiId}.
	 *
	 */
	public static String parse(Object obj) {
		@SuppressWarnings("unchecked")
		Collection<ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(
					new Class[]{JsonApiId.class},
					new Class[]{JsonApiIgnore.class},
					obj)
				.values();

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

	private static JsonApiParsingException mustHaveOne(Class<? extends Object> cls) {
		return new JsonApiParsingException (
				cls.getCanonicalName() +
					" class hierarchy must have an accessible property annotated with @" +
							JsonApiId.class.getSimpleName());
	}

	private static JsonApiParsingException mustHaveSingle(Class<? extends Object> cls) {
		return new JsonApiParsingException(
				cls.getCanonicalName() +
					" class hierarchy must have a single accessible property annotated with @" +
						JsonApiId.class.getSimpleName());
	}
}
