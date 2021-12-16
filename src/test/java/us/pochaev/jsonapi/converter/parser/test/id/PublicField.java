package us.pochaev.jsonapi.converter.parser.test.id;

import us.pochaev.jsonapi.converter.annotations.JsonApiId;

public class PublicField {
	@JsonApiId public Object id = Constants.ID;
}
