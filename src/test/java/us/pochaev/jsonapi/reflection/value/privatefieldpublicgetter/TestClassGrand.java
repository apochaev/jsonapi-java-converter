package us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter;

import us.pochaev.jsonapi.reflection.TestMemberAnnotation;

public class TestClassGrand extends TestClassChild {

	@TestMemberAnnotation
	public String getnonGetter() {
		return "getNonGetter";
	}
}
