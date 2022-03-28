package us.pochaev.jsonapi.v1_0.converter.to.id_parser;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject
public class TwoAnnotatedFields {
	@JsonApiId
	public Object id = Constants.ID;

	@JsonApiId
	public Object id2 = Constants.ID;

}
