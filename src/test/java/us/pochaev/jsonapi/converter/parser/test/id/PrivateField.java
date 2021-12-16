package us.pochaev.jsonapi.converter.parser.test.id;

import us.pochaev.jsonapi.converter.annotations.JsonApiId;

public class PrivateField {
	@JsonApiId private Object id = Constants.ID;
}