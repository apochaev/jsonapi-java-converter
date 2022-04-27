package us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter;

import us.pochaev.jsonapi.reflection.annotation.JsonApiTestAnnotation;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;

public class TestClass {

	@JsonApiTestAnnotation
	@JsonApiIgnore
	private Object privateIgnoredTestClassField = "privateIgnoredTestClassField";

	@JsonApiTestAnnotation
	private Object privateTestClassField = "privateTestClassField";

	public Object getPrivateTestClassField () {
		return privateTestClassField;
	}

	@JsonApiTestAnnotation
	public Object getTestClassGetter() {
		return "testClassGetter";
	}

	@JsonApiTestAnnotation
	@JsonApiIgnore
	public Object getIgnoredTestClassGetter() {
		return "ignoredTestClassGetter";
	}


}
