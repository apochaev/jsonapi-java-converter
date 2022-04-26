package us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter;

import us.pochaev.jsonapi.reflection.TestMemberAnnotation;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;

public class TestClass {

	@TestMemberAnnotation
	@JsonApiIgnore
	private Object privateIgnoredTestClassField = "privateIgnoredTestClassField";

	@TestMemberAnnotation
	private Object privateTestClassField = "privateTestClassField";

	public Object getPrivateTestClassField () {
		return privateTestClassField;
	}

	@TestMemberAnnotation
	public Object getTestClassGetter() {
		return "testClassGetter";
	}

	@TestMemberAnnotation
	@JsonApiIgnore
	public Object getIgnoredTestClassGetter() {
		return "ignoredTestClassGetter";
	}


}
