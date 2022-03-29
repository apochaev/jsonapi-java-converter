package us.pochaev.jsonapi.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.pochaev.jsonapi.reflection.ancestry.AncestryUtils;

public class ValueUtils {
	static final String PREFIX_IS = "is";
	static final String PREFIX_GET = "get";

	/**
	 * Find all annotated fields find a matching public getter if no getter and not declared field drop it.
	 *
	 */
	public static Collection<ValueDescriptor> getValueDescriptors(Class<? extends Annotation> annotationClass, Object object) {
		Class<?> objectClass = object.getClass();

		Stack<Class<?>> objectAncestry = AncestryUtils.getAncestry(object);


		Map<String, Field> fields = findValueFields(annotationClass, objectClass)
				.stream()
				.collect(Collectors.toMap(Field::getName, Function.identity()));

//		findValueGetters(fields, objectClass);

		return Collections.emptyList();
	}



	/**
	 * Returns non-synthetic non-static fields annotated with the given annotation class anywhere in the objectClass hierarchy.
	 *
	 * @param annotationClass Annotation class, must not be null.
	 * @param objectClass Class to introspect, must not be null.
	 * @return
	 */
	protected static List<Field> findValueFields(Class<? extends Annotation> annotationClass, Class<?> objectClass) {
		Objects.requireNonNull(annotationClass);
		return Arrays
			.stream(objectClass.getFields())
			.filter(field -> ! field.isSynthetic())
			.filter(field -> ! Modifier.isStatic(field.getModifiers()))
			.filter(field -> (field.getAnnotation(annotationClass) != null))
			.collect(Collectors.toList());
	}

	/**
	 * Returns non-static public methods of the objectClass that match getter name convention for the provided field names.
	 * @param keySet
	 * @param objectClass
	 */
	private static void findValueGetters(Set<String> fieldNames, Class<?> objectClass) {
//		return Arrays
//				.stream(objectClass.getme())

	}




//	?Optional<Method> optionalGetter = findGetter(field, cls);
	/**
	 * Returns a concrete instance accessible getter
	 * @param field
	 * @param cls
	 * @return
	 * TODO update JavaDoc
	 */
	private static Optional<Method> findGetter(Collection<String> names, Class<? extends Object> cls) {
//		for (Method method : cls.getMethods()) {
//			String getterName = createGetterName(field);
//			if (method.getName().equals(getterName)) {
//
//			}
//
//			if (isConcreteInstanceMember(method)) {
//				if (method.isAccessible()) {
//
//				}
//			}
//		}

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
