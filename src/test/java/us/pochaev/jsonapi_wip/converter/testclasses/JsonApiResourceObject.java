package us.pochaev.jsonapi_wip.converter.testclasses;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject("JsonApiResourceObject")
public class JsonApiResourceObject {
	@JsonApiId
	private Object id;

	public JsonApiResourceObject() {
	}

	public JsonApiResourceObject(Object id) {
		this.id = id;
	}

	public Object getId() {
		return id == null ? null : id.toString();
	}

}
