package us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter;

import us.pochaev.jsonapi.reflection.annotation.JsonApiTestAnnotation;

public class TestClassChild extends TestClass {
	@JsonApiTestAnnotation
	private Object privateTestClassChildField = "privateTestClassChildField";

	public Object getPrivateTestClassChildField () {
		return privateTestClassChildField;
	}

	@Override
	public Object getPrivateTestClassField() {
		return privateTestClassChildField;
	}


}
