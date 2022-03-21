package us.pochaev.jsonapi_wip.converter.parser.test.id;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiId;

@JsonApiObject
public class PublicField {
	@JsonApiId
	public Object id = Constants.ID;
}
