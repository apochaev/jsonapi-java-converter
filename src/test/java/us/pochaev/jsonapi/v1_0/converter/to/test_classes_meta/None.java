package us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.test_classes_id.Constants;

@JsonApiObject
public class None {
	@JsonApiId
	public Object id = Constants.ID;
}
