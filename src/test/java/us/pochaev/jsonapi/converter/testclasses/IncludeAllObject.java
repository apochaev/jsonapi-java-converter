package us.pochaev.jsonapi.converter.testclasses;

import java.util.UUID;

import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.converter.annotations.JsonApiObject;

@JsonApiObject("IncludeAllObject")

public class IncludeAllObject {
	@JsonApiId
	UUID id;

	public String name;

	protected String alias;

	@SuppressWarnings("unused")
	private String identifier;

	public String getGetterOnly() {
		return null;
	};

	private String fieldAndGetter;

	public String getFieldAndGetter() {
		return fieldAndGetter;
	}

	@JsonApiIgnore
	public String ignore;
}
