package us.pochaev.jsonapi.v1_0.converter.to.test_classes_meta;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiMetaItem;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject
public class All {
	public static final Object ID = null;
	public static final Object META_ITEM_ANNOTATED_VALUE = "metaItemAnnotated_value";
	public static final Object META_ITEM_ANNOTATED_RENAMED_VALUE = "metaItemAnnotatedRenamed_value";
	public static final Object META_ITEM_GETTER_VALUE = "metaItemGetter_value";
	public static final Object META_ITEM_GETTER_RENAMED_VALUE = "metaItemGetterRenamed_value";

	@JsonApiId
	public Object id = ID;

	@JsonApiMetaItem
	public Object metaItemAnnotated = META_ITEM_ANNOTATED_VALUE;

	public Object notAnnotated = null;

	@JsonApiMetaItem
	@JsonApiIgnore
	public Object metaItemAnnotatedIgnored = null;

	@JsonApiMetaItem("metaItemAnnotatedRenamed")
	public Object metaItemAnnotatedRenamed = META_ITEM_ANNOTATED_RENAMED_VALUE;

	public Object getSomeProperty() {
		return null;
	}

	@JsonApiMetaItem
	public Object getMetaItemGetter() {
		return META_ITEM_GETTER_VALUE;
	}

	@JsonApiMetaItem("metaItemGetterRenamed")
	public Object getMetaItemGetterRenamed() {
		return META_ITEM_GETTER_RENAMED_VALUE;
	}

	@JsonApiMetaItem
	@JsonApiIgnore
	public Object getMetaItemGetterIgnored() {
		return null;
	}


}

