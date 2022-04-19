package us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter;

import us.pochaev.jsonapi.reflection.TestMemberAnnotation;

public class TestClass {
	@TestMemberAnnotation
	private Object privateTestClassField = "privateTestClassField";

	public Object getPrivateTestClassField () {
		return privateTestClassField;
	}

	@TestMemberAnnotation
	public Object getTestClassGetter() {
		return "testClassGetter";
	}


}
