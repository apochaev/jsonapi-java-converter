package us.pochaev.jsonapi.v1_0.converter.to.id_parser;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject
public class PublicField {
	@JsonApiId
	public Object id = Constants.ID;
}
