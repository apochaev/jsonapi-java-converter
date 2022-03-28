package us.pochaev.jsonapi.reflection;

public class TestMostParentMostChild extends TestMostParent {
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
	public Object publicField = "publicFieldChild";

	@TestMemberAnnotation
	Object packageField;

	@TestMemberAnnotation
	private Object getPrivateField() {
		return privateField;
	}

	@Override
	@TestMemberAnnotation
	protected Object getProtectedField() {
		return protectedField;
	}

	@Override
	@TestMemberAnnotation
	public Object getPublicField() {
		return publicField;
	}

	@Override
	@TestMemberAnnotation
	Object getPackageField() {
		return packageField;
	}

	@TestMemberAnnotation
	private void somePrivateMethod() {

	}

	@Override
	@TestMemberAnnotation
	protected void someProtectedMethod() {

	}

	@Override
	@TestMemberAnnotation
	public void somePublicMethod() {

	}

	@Override
	@TestMemberAnnotation
	void somePackageMethod() {

	}
}
