package us.pochaev.jsonapi.converter.parser;

import us.pochaev.jsonapi.annotations.JsonApiObject;

public class JsonApiObjectParser {
	//TODO could be static
	public String parseType(Object obj) {
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

		if (type.trim().length() > 0)
			return type;

		throw new IllegalArgumentException(
				"@" + JsonApiObject.class.getSimpleName() + " value may not be blank");
	}
}
