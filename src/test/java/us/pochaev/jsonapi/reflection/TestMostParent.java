package us.pochaev.jsonapi.reflection;

public class TestMostParent {
	@TestMemberAnnotation
	private static Object privateStaticField;

	@TestMemberAnnotation
	protected static Object protectedStaticField;

	@TestMemberAnnotation
	public static Object publicStaticField;

	@TestMemberAnnotation
	static Object packageStaticField;

	@TestMemberAnnotation
	private static Object privateStaticGetter() {
		return privateStaticField;
	};

	@TestMemberAnnotation
	protected static Object protectedStaticGetter() {
		return protectedStaticField;
	};

	@TestMemberAnnotation
	public static Object publicStaticGetter() {
		return publicStaticField;
	};

	@TestMemberAnnotation
	static Object packageStaticGetter() {
		return packageStaticField;
	};

	@TestMemberAnnotation
	private Object privateField;

	@TestMemberAnnotation
	protected Object protectedField;

	@TestMemberAnnotation
	public Object publicField = "publicFieldParent";

	@TestMemberAnnotation
	Object packageField;

	@TestMemberAnnotation
	private Object getPrivateField() {
		return privateField;
	}

	@TestMemberAnnotation
	protected Object getProtectedField() {
		return protectedField;
	}

	@TestMemberAnnotation
	public Object getPublicField() {
		return publicField;
	}

	@TestMemberAnnotation
	Object getPackageField() {
		return packageField;
	}

	@TestMemberAnnotation
	private void somePrivateMethod() {

	}

	@TestMemberAnnotation
	protected void someProtectedMethod() {

	}

	@TestMemberAnnotation
	public void somePublicMethod() {

	}

	@TestMemberAnnotation
	void somePackageMethod() {

	}
}
