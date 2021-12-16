package us.pochaev.jsonapi.converter.parser;

import java.lang.reflect.Field;

import us.pochaev.jsonapi.converter.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiIgnore;

public class JsonApiIdFieldFinder {

	public static String find(Object obj) {
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

				if (field.canAccess(obj)) {
					JsonApiId jsonApiId = field.getAnnotation(JsonApiId.class);
					if (jsonApiId != null) {
						validateNotJsonApiAttribute(field);
						validateNotJsonApiIgnore(field);
						validateNotExists(idField);
						idField = field;
					}
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
//		field.setAccessible(true);
		try {
			value = field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		String result = value == null ? null : value.toString();
		return result;
	}

	private static void validateExists(Field field) {
		if (field == null) {
			throw new IllegalStateException(
					"Class hierarchy must have a public property annotated with @" + JsonApiId.class.getSimpleName());
		}
	}

	private static void validateNotExists(Field idField) {
		if (idField != null) {
			throw new IllegalStateException(
					"Class hierarchy must have a single public proeprty annotated with @" + JsonApiId.class.getSimpleName());
		}
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
