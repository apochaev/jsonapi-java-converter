package us.pochaev.jsonapi.converter.testclasses;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;

@JsonApiObject("JsonApiResourceObject")
public class JsonApiResourceObject {
	@JsonApiId
	private Object id;

	public JsonApiResourceObject() {
	}

	public JsonApiResourceObject(Object id) {
		this.id = id;
	}

}
