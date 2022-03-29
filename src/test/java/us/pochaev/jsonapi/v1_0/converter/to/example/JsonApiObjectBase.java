package us.pochaev.jsonapi.v1_0.converter.to.example;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;

public class JsonApiObjectBase {
	@JsonApiId
	private String id;

	public String getId( ) {
		return id;
	}
}
