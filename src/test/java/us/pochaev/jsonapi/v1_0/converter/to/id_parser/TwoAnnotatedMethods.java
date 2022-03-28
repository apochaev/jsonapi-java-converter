package us.pochaev.jsonapi.v1_0.converter.to.id_parser;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject
public class TwoAnnotatedMethods {

	private Object id = Constants.ID;


	private Object id2 = Constants.ID;

	@JsonApiId
	public Object getId() {
		return id;
	}

	@JsonApiId
	public Object getId2() {
		return id2;
	}



}
