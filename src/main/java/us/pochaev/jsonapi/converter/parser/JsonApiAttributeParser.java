package us.pochaev.jsonapi.converter.parser;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.pochaev.jsonapi.converter.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiIgnore;

public class JsonApiAttributeParser {

	public static Map<String, AttributeData> findAttributes(Object obj) {
		return findAttributes(obj, null, new HashMap<>());
	}

	private static Map<String, AttributeData> findAttributes(Object obj, AttributeData idAttribute, Map<String, AttributeData> attributes) {

		Class<?> cls = obj.getClass();

		Field[] fields = cls.getDeclaredFields();
		if (fields != null) {
			for (Field field: fields) {
				if (field.isSynthetic()) {
					continue;
				}

				AttributeData attribute = new AttributeData();

				JsonApiId jsonApiId = field.getAnnotation(JsonApiId.class);
				if (jsonApiId != null) {
					validateNotJsonApiAttribute(field);
					validateNotJsonApiIgnore(field);
					validateNotExists(idAttribute);
					attribute.id = true;
				} else {

				}

				attribute.name = field.getName();

				attributes.put(attribute.name,attribute);

			}
		}
		return attributes;
	}

	private static void validateNotExists(AttributeData idAttribute) {
		throw new IllegalStateException(
				"Class hierarchy must have a single field annotated with @" + JsonApiId.class.getSimpleName());
	}

	private static void validateNotJsonApiIgnore(Field field) {
		JsonApiIgnore jsonApiIgnore = field.getAnnotation(JsonApiIgnore.class);
		if (jsonApiIgnore !=null) {
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


	/**
	 * Returns a collection of fields in the class and its hierarchy that are eligible for marshalling
	 * into a JSON API resource object.
	 *
	 * List includes any fields that represent public properties that
	 * are not annotated with
	 *
	 * @param clazz
	 * @param idFields
	 * @return
	 */
	public static Collection<Field> findIdFields(Class<? extends Object> clazz, List<Field> idFields) {

		Field[] fields = clazz.getDeclaredFields();
		if (fields != null) {
			for (Field field: fields) {
				if (field.isSynthetic()) {
					continue;
				}

				JsonApiId jsonApiId = field.getAnnotation(JsonApiId.class);
				if (jsonApiId != null) {
					validateNotJsonApiAttribute(field);

					validateNotJsonApiIgnore(field);

					if (idFields.size() > 0) {
						throw new IllegalArgumentException(
								"Class hierarchy must have a single field annotated with @" + JsonApiId.class.getSimpleName());
					}
					idFields.add(field);
				}
			}
		}

		if (clazz.getSuperclass() != null) {
			return findIdFields(clazz.getSuperclass(), idFields);
		}

		return idFields;
	}
}
