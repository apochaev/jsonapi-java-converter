package us.pochaev.jsonapi.v1_0.converter.to.test_classes_id;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject
public class PrivateAnnotatedFieldPublicGetter {
	@JsonApiId
	private Object id = Constants.ID;

	public Object getId() {
		return id;
	}
}