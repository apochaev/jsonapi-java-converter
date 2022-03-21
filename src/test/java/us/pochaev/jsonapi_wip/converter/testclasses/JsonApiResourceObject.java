package us.pochaev.jsonapi_wip.converter.testclasses;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiId;

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
