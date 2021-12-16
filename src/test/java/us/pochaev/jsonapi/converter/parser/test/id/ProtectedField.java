package us.pochaev.jsonapi.converter.parser.test.id;

import us.pochaev.jsonapi.converter.annotations.JsonApiId;

public class ProtectedField {
	@JsonApiId protected Object id = Constants.ID;
}