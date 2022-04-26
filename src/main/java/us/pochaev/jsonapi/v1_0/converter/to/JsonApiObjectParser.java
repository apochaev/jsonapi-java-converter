package us.pochaev.jsonapi.v1_0.converter.to;

import java.util.Objects;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiParsingException;
import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiSpecificationViolation;
import us.pochaev.jsonapi.v1_0.converter.to.membername.MemberNameFactory;

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
	 * Will throw {@link JsonApiParsingException} if JSON API Object type can not be determined.
	 * @param obj
	 * @return JSON API Object type.
	 */
	static String parseType(Object obj) {
		Objects.requireNonNull(obj);

		Class<? extends Object> objClass = obj.getClass();

		String type = getJsonApiObjectType(objClass);

		try {
			return MemberNameFactory.create(type);
		} catch (JsonApiSpecificationViolation e) {
			String message = objClass.getCanonicalName() +
					" @" + JsonApiObject.class.getSimpleName() +
					" value must be valid.";


			throw new JsonApiParsingException(message, e);
		}
	}

	private static String getJsonApiObjectType(Class<? extends Object> objClass) {
		JsonApiObject jsonApiObject = objClass.getAnnotation(JsonApiObject.class);
		if (jsonApiObject == null) {
			throw mustBeAnnotatedException(objClass);
		}

		String type = jsonApiObject.value();
		if (JsonApiObject.DEFAULT_VALUE.equals(type)) {
			type = objClass.getSimpleName();
		}

		return type;
	}

	private static JsonApiParsingException mustBeAnnotatedException(Class<? extends Object> objClass) {
		return new JsonApiParsingException(
						objClass.getCanonicalName() +
							" must be annotated with @" +
								JsonApiObject.class.getSimpleName() +
									".");
	}
}
