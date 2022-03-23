package us.pochaev.jsonapi.v1_0.converter.to;

import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withParameters;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.ReflectionUtils;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiId;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiAttribute;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiIgnore;

class JsonApiIdParser {

	/**
	 * Returns value of JSON API Id
	 *
	 * @param obj JsonApiObject
	 * @return
	 * TODO document
	 */
	public static String parse(Object obj) {

		Class<JsonApiId> jsonApiIdClass = JsonApiId.class;
		Set<Field> fields = findFieldsWithAnnotation(obj, jsonApiIdClass);

//		Set<Method> getters = findGetters()

		Set<Method> getters = ReflectionUtils.getAllMethods(obj.getClass(),
			      withParameters(new Class<?>[0]),
			      withAnnotation(jsonApiIdClass)); //TODO remove dependency



//		int elementCount = fields.size() + getters.size() + properties.size();

		int elementCount = fields.size();
		switch (elementCount) {
			case 1: {
					Field field = fields.iterator().next();
					if (field.canAccess(obj)) {
						return String.valueOf(
								 getObjectValue(obj, field));
					} else {
						try {
							//TODO need to find read method manually. Property descriptor requires set too.
							PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
							Method readMethod = pd.getReadMethod();
							if (readMethod != null) {
								if (readMethod.canAccess(obj)) {
									try {
										return String.valueOf(
												readMethod.invoke(obj, new Object[0]));
									} catch (IllegalAccessException e) {
										throw new RuntimeException(e);
									} catch (IllegalArgumentException e) {
										throw new RuntimeException(e);
									} catch (InvocationTargetException e) {
										throw new RuntimeException(e);
									}
								}
							}
						} catch (IntrospectionException e) {
							throw new RuntimeException(e);
						}
						throw mustBeAccessible(field);
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



	private static char[] getObjectValue(Object obj, Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Set<Field> findFieldsWithAnnotation(Object obj, Class<JsonApiId> jsonApiIdClass) {
		return ReflectionUtils.getAllFields(obj.getClass(),
				withAnnotation(jsonApiIdClass));
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

	private static RuntimeException mustBeAccessible(Field field) {
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
