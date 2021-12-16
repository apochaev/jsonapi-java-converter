package us.pochaev.jsonapi.converter.parser.test.id;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;

@JsonApiObject
public class PrivateFieldPublicGetter {
	@JsonApiId
	private Object id = Constants.ID;

	public Object getId() {
		return id;
	}
}