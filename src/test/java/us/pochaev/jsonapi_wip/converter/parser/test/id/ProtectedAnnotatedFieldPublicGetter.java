package us.pochaev.jsonapi_wip.converter.parser.test.id;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.id_parser.Constants;

@JsonApiObject
public class ProtectedAnnotatedFieldPublicGetter {
	@JsonApiId
	protected Object id = Constants.ID;
	
	public Object getId() {
		return this.id;
	}
}