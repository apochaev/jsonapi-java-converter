package us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter;

import us.pochaev.jsonapi.reflection.TestMemberAnnotation;

public class TestClassChild extends TestClass {
	@TestMemberAnnotation
	private Object privateTestClassChildField = "privateTestClassChildField";

	public Object getPrivateTestClassChildField () {
		return privateTestClassChildField;
	}

	@Override
	public Object getPrivateTestClassField() {
		return privateTestClassChildField;
	}


}
