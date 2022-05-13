package us.pochaev.jsonapi.v1_0.converter.to.example;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;

public abstract class AbstractJsonApiObject {
	@JsonApiId
	private final String id;

	public AbstractJsonApiObject(String id) {
		this.id = id;
	}

	public String getId( ) {
		return id;
	}

}
