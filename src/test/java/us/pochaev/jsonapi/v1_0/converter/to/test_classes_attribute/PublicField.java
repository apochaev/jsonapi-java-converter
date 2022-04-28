package us.pochaev.jsonapi.v1_0.converter.to.test_classes_attribute;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject
public class PublicField {
	@JsonApiId
	public Object id;

	public Object attribute1;

	@JsonApiAttribute
	public Object attribute2;

	@JsonApiAttribute("attribute3")
	public Object attributeThree;
}
