package us.pochaev.jsonapi.v1_0.converter.to;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import us.pochaev.jsonapi.reflection.ReflectionUtils;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiAttribute;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiIgnore;

class JsonApiIdParser {

	/**
	 * Returns value of JSON API Id
	 *
	 * Inspects all public fields and methods to find one annotated with {@link JsonApiId}
	 *
	 * @param obj JsonApiObject
	 * @return
	 * TODO document
	 */
	public static String parse(Object obj) {

		Class<JsonApiId> jsonApiIdClass = JsonApiId.class;

		Collection<Field> fields = ReflectionUtils.getAnnotatedInstanceFields(jsonApiIdClass, obj.getClass());



		obj.getClass().getMethods();



//		Set<Method> getters = findGetters()

		Set<Method> getters = new HashSet<>();



//		int elementCount = fields.size() + getters.size() + properties.size();

		int elementCount = fields.size();
		switch (elementCount) {
			case 1: {
					Field field = fields.iterator().next();
					if (field.canAccess(obj)) {
						return String.valueOf(
								 getObjectValue(obj, field));
					} else {
							Method readMethod = findGetter(obj, field);
							if (readMethod != null) {
								if (readMethod.canAccess(obj)) {

									return String.valueOf(
											getObjectValue(obj, readMethod));
								}
								throw getterMustBeAccessible(readMethod);
							}

						throw fieldMustBeAccessible(field);
					}
				}
			case 0: throw mustHaveOne(obj.getClass());
			default : throw mustHaveSingle(obj.getClass());
		}

//		 Field field = findIdField(obj, obj.getClass(), null);
//		 validateExists(obj.getClass(), field);
//		 return String.valueOf(
//				 getObjectValue(obj, field));
	}


	private static Method findGetter(Object obj, Field field) {
		Class<?> fieldType = field.getType();

		String prefix = getPrefix(fieldType);
		String name = prefix + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

		for (Method method : field.getDeclaringClass().getMethods()) {

			if (method.getName().equals(name) &&
				method.getParameterCount() == 0 &&
				method.getReturnType().equals(fieldType)) {
					return method;
			}
		}

		return null;
	}


	private static String getPrefix(Class<?> fieldType) {
		String prefix = "get";
		if (boolean.class.equals(fieldType) || Boolean.class.equals(fieldType)) {
			prefix = "is";
		}
		return prefix;
	}

	private static Object getObjectValue(Object obj, Field field) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static Object getObjectValue(Object obj, Method readMethod) {
		try {
			return readMethod.invoke(obj, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}


	private static Field findIdField(Object obj, Class<?> cls, Field idField) {
		Field[] fields = cls.getDeclaredFields();
		if (fields != null) {
			for (Field field: fields) {
				if (field.isSynthetic()) {
					continue;
				}

				JsonApiId jsonApiId = field.getAnnotation(JsonApiId.class);
				if (jsonApiId != null) {
					validateNotJsonApiAttribute(field);
					validateNotJsonApiIgnore(field);
					validateNotExists(cls, idField);
					idField = field;
				}
			}
		}

		if (cls.getSuperclass() != null) {
			return findIdField(obj, cls.getSuperclass(), idField);
		}

		return idField;
	}

	private static String getStringValue(Object obj, Field field) {
		Object value = null;

		if (field.canAccess(obj)) {
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		} else {
//			try {
//				value = ReflectionUtils.getProperty(obj, field.getName());
//			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//				throw new RuntimeException(e);
//			}
		}

		String result = value == null ? null : value.toString();
		return result;
	}

	private static void validateExists(Class<? extends Object> cls, Field field) {
		if (field == null) {
			throw mustHaveOne(cls);
		}
	}

	private static RuntimeException getterMustBeAccessible(Method method) {
		return new IllegalStateException(
				method.getDeclaringClass().getCanonicalName() + "#" + method.getName() + " field annotated with @" + JsonApiId.class.getSimpleName() + " or its getter must be accessible.");
	}

	private static RuntimeException fieldMustBeAccessible(Field field) {
		return new IllegalStateException(
				field.getDeclaringClass().getCanonicalName() + "#" + field.getName() + " field annotated with @" + JsonApiId.class.getSimpleName() + " must be accessible.");
	}

	private static RuntimeException mustHaveOne(Class<? extends Object> cls) {
		return new IllegalStateException(
				cls.getCanonicalName() + " class hierarchy must have an accessible property annotated with @" + JsonApiId.class.getSimpleName());
	}

	private static void validateNotExists(Class<? extends Object> cls, Field idField) {
		if (idField != null) {
			throw mustHaveSingle(cls);
		}
	}

	private static IllegalStateException mustHaveSingle(Class<? extends Object> cls) {
		return new IllegalStateException(
				cls.getCanonicalName() + " class hierarchy must have a single accessible property annotated with @" + JsonApiId.class.getSimpleName());
	}

	private static void validateNotJsonApiIgnore(Field field) {
		JsonApiIgnore jsonApiIgnore = field.getAnnotation(JsonApiIgnore.class);
		if (jsonApiIgnore != null) {
			throw new IllegalStateException(
					"Field annotated with @" + JsonApiId.class.getSimpleName() +
					" may not be annotated with @" + JsonApiIgnore.class.getSimpleName());
		}
	}

	private static void validateNotJsonApiAttribute(Field field) {
		JsonApiAttribute jsonApiAttribute = field.getAnnotation(JsonApiAttribute.class);
		if (jsonApiAttribute != null) {
			throw new IllegalStateException(
					"Field annotated with @"  + JsonApiId.class.getSimpleName() +
					" may not be annotated with @" + JsonApiAttribute.class.getSimpleName());
		}
	}
}
