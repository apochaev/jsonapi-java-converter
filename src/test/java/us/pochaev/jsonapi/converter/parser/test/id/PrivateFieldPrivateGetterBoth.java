package us.pochaev.jsonapi.converter.parser.test.id;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;

@JsonApiObject
public class PrivateFieldPrivateGetterBoth {
	@JsonApiId
	private Object id = Constants.ID;

	@JsonApiId
	private Object getId() {
		return id;
	}
}