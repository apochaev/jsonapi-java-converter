package us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter;

import us.pochaev.jsonapi.reflection.annotation.JsonApiTestAnnotation;

public class TestClassGrand extends TestClassChild {

	@JsonApiTestAnnotation
	public String getnonGetter() {
		return "getNonGetter";
	}
}
