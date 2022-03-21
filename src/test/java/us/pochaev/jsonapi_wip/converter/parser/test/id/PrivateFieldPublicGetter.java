package us.pochaev.jsonapi_wip.converter.parser.test.id;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiId;

@JsonApiObject
public class PrivateFieldPublicGetter {
	@JsonApiId
	private Object id = Constants.ID;

	public Object getId() {
		return id;
	}
}