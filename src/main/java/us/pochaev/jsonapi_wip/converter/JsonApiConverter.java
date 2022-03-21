package us.pochaev.jsonapi_wip.converter;

import us.pochaev.jsonapi_wip.converter.to.ToJsonApiConverter;

public class JsonApiConverter {

	private JsonApiConverter() {

	}

	public static String toJsonApiString(Object obj) {
		return ToJsonApiConverter.convert(obj);
	}

	public static <T> T fromJsonApiString(String jsonApiObjectString, Class<T> cls) {
		return new FromJsonApiConverter().convert(jsonApiObjectString, cls);
	}
}
