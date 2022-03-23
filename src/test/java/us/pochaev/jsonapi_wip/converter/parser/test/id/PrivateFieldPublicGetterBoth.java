package us.pochaev.jsonapi_wip.converter.parser.test.id;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.Constants;

@JsonApiObject
public class PrivateFieldPublicGetterBoth {
	@JsonApiId
	private Object id = Constants.ID;

	@JsonApiId
	public Object getId() {
		return id;
	}
}