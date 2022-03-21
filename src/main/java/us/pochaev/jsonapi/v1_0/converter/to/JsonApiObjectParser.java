package us.pochaev.jsonapi.v1_0.converter.to;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

/**
 * Parser that determines JSON API Object type using {@link JsonApiObject} annotation.
 *
 * @author apochaev
 *
 */
class JsonApiObjectParser {

	/**
	 * Returns JSON API Object type as declared by the {@link JsonApiObject} annotation of the
	 * parameter's class.
	 *
	 * Will throw {@link IllegalArgumentException} if JSON API Object type can not be determined.
	 * @param obj
	 * @return JSON API Object type.
	 */
	static String parseType(Object obj) {
		JsonApiObject jsonApiObject = obj.getClass().getAnnotation(JsonApiObject.class);
		if (jsonApiObject == null)
			throw new IllegalStateException(
					"Class must be annotated with @" + JsonApiObject.class.getSimpleName());

		String type = jsonApiObject.value();
		if (type == null)
			throw new IllegalArgumentException(
					"@" + JsonApiObject.class.getSimpleName() + " value may not be null");

		if (type.equals(JsonApiObject.DEFAULT_VALUE))
			return obj.getClass().getSimpleName();

		if (type.length() != type.trim().length())
			throw new IllegalArgumentException(
					"@" + JsonApiObject.class.getSimpleName() + " value may not start or end with a space");

		if (type.trim().length() > 0)
			return type;

		throw new IllegalArgumentException(
				"@" + JsonApiObject.class.getSimpleName() + " value may not be blank");
	}
}
