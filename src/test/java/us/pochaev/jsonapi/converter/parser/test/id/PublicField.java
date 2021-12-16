package us.pochaev.jsonapi.converter.parser.test.id;

import us.pochaev.jsonapi.annotations.JsonApiObject;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;

@JsonApiObject
public class PublicField {
	@JsonApiId
	public Object id = Constants.ID;
}
