package us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiMetaItem;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject
public class PublicField {
	@JsonApiId
	public Object id;

	@JsonApiMetaItem
	public Object metaItem1;

	public Object metaItem2;

}
