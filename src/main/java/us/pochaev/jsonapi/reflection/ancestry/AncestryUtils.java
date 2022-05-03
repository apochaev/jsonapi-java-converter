package us.pochaev.jsonapi.reflection.ancestry;

import java.util.Stack;

/**
 * @deprecated Switch to value-utils project
 */
@Deprecated
public class AncestryUtils {

	public static Stack<Class<?>> getAncestry(Object object) {
		return getAncestry(object.getClass(), new Stack<Class<?>>());
	}

	private static Stack<Class<?>> getAncestry(Class<?> objectClass, Stack<Class<?>> ancestry) {

		ancestry.push(objectClass);

		Class<?> superClass = objectClass.getSuperclass();
		if (superClass.equals(Object.class)) {
			ancestry.push(superClass);
			return ancestry;
		}

		return getAncestry(superClass, ancestry);
	}
}
