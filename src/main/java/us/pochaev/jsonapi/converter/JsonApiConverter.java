package us.pochaev.jsonapi.converter;

public class JsonApiConverter {

	private JsonApiConverter() {

	}

	public static String toJsonApiString(Object obj) {
		return new ToJsonApiConverter().convert(obj);
	}


}
