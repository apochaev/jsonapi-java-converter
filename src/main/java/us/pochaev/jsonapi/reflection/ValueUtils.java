package us.pochaev.jsonapi.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

public class ValueUtils {
	static final String PREFIX_IS = "is";
	static final String PREFIX_GET = "get";

//	?Optional<Method> optionalGetter = findGetter(field, cls);
	/**
	 * Returns a concrete instance accessible getter
	 * @param field
	 * @param cls
	 * @return
	 * TODO update JavaDoc
	 */
	private static Optional<Method> findGetter(Field field, Class<? extends Object> cls) {
		for (Method method : cls.getMethods()) {
			String getterName = createGetterName(field);
			if (method.getName().equals(getterName)) {

			}

			if (isConcreteInstanceMember(method)) {
				if (method.isAccessible()) {

				}
			}
		}

		// TODO Auto-generated method stub
		return null;
	}

	protected static boolean isConcreteInstanceMember(Member member) {
		int modifiers = member.getModifiers();
		return !Modifier.isStatic(modifiers) &&
			   !Modifier.isAbstract(modifiers);
	}



	protected static String createGetterName(Field field) {
		if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
			return PREFIX_IS + capitalize(field.getName());
		}
		return PREFIX_GET + capitalize(field.getName());
	}

	protected static String capitalize(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
}
