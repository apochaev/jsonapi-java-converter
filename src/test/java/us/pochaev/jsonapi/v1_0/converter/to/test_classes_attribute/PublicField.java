package us.pochaev.jsonapi.v1_0.converter.to.test_classes_attribute;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;
import us.pochaev.jsonapi.v1_0.converter.to.test_classes_id.Constants;

@JsonApiObject
public class PublicField {
	@JsonApiId
	public Object id = Constants.ID;

	public Object attribute1 = "attributeValue1";
}
