package us.pochaev.jsonapi.converter.parser;

import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withModifier;
import static org.reflections.ReflectionUtils.withParameters;
import static org.reflections.ReflectionUtils.withPrefix;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import org.reflections.ReflectionUtils;

import us.pochaev.jsonapi.converter.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiIgnore;

public class JsonApiIdFieldFinder {

	public static String find(Object obj) {

		@SuppressWarnings("unchecked")
		Set<Field> fields = ReflectionUtils.getAllFields(obj.getClass(),
//				withModifier(Modifier.PUBLIC),
				withAnnotation(JsonApiId.class));

		@SuppressWarnings("unchecked")
		Set<Method> getters = ReflectionUtils.getAllMethods(obj.getClass(),
			      withModifier(Modifier.PUBLIC),
			      withPrefix("get"),
			      withParameters(null),
			      withAnnotation(JsonApiId.class));

		@SuppressWarnings("unchecked")
		Set<Method> properties = ReflectionUtils.getAllMethods(obj.getClass(),
			      withModifier(Modifier.PUBLIC),
			      withPrefix("get"),
			      withParameters(null),
			      withAnnotation(JsonApiId.class));

		int elementCount = fields.size() + getters.size() + properties.size();
		switch (elementCount) {
			case 1: break;
			case 0: throw mustHaveOne();
			default : throw mustHaveSingle();
		}


		 Field field = findIdField(obj, obj.getClass(), null);
		 validateExists(field);
		 return getStringValue(obj, field);
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
					validateNotExists(idField);
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

	private static void validateExists(Field field) {
		if (field == null) {
			throw mustHaveOne();
		}
	}

	private static RuntimeException mustHaveOne() {
		return new IllegalStateException(
				"Class hierarchy must have a public property annotated with @" + JsonApiId.class.getSimpleName());
	}

	private static void validateNotExists(Field idField) {
		if (idField != null) {
			throw mustHaveSingle();
		}
	}

	private static IllegalStateException mustHaveSingle() {
		return new IllegalStateException(
				"Class hierarchy must have a single public proeprty annotated with @" + JsonApiId.class.getSimpleName());
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
