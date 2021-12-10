package us.pochaev.jsonapi.converter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import us.pochaev.jsonapi.converter.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.converter.annotations.JsonApiObject;

class ToJsonApiConverter {
	private final static ObjectMapper mapper = new ObjectMapper();

	public String convert(Object obj) {
		Objects.requireNonNull(obj);

		String type = getType(obj);

		Field idField = findIdField(obj);
		String id = getStringValue(obj, idField);

		Map<String, Field> fieldMap = findAttributeFields(obj.getClass(), new HashMap<>());
		Map<String, Object> attributeMap = getFieldValues(obj, fieldMap);

		return createJsonApiObjectString(id, type, attributeMap);
	}

	private Map<String, Object> getFieldValues(Object obj, Map<String, Field> fieldMap) {
		Map<String, Object> result = new HashMap<>();

		for(String key : fieldMap.keySet()) {
			Field field = fieldMap.get(key);
			result.put(key, getObjectValue(obj, field));
		}

		return result;
	}

	private Object getObjectValue(Object obj, Field field) {
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Field> findAttributeFields(Class<? extends Object> clazz, Map<String, Field> fieldMap) {
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null) {
			for (Field field: fields) {
				JsonApiIgnore jsonApiIgnore = field.getAnnotation(JsonApiIgnore.class);
				if (jsonApiIgnore == null) {
					if (fieldMap.containsKey( field.getName())) {
						//this should be a compile time exception
						throw new RuntimeException("Duplicate field " + field.getName() + " in the class hierarchy.");
					}
					fieldMap.put(field.getName(), field);
				}
			}
		}

		if (clazz.getSuperclass() != null) {
			return findAttributeFields(clazz.getSuperclass(), fieldMap);
		}

		return fieldMap;
	}

	private String getType(Object obj) {
		JsonApiObject jsonApiObject = obj.getClass().getAnnotation(JsonApiObject.class);
		if (jsonApiObject != null) {
			String type = jsonApiObject.value();
			if (type != null && type.trim().length() > 0) {
				return type;
			}
			throw new IllegalArgumentException(
					"@" + JsonApiObject.class.getSimpleName() + " value may not be blank");
		}
		throw new IllegalArgumentException(
				"Class must be annotated with @" + JsonApiObject.class.getSimpleName());
	}

	private String createJsonApiObjectString(String id, String type, Map<String, Object> attributeMap) {
	    ObjectNode user = mapper.createObjectNode();
	    user.put("id", id);
	    user.put("type", type);

	    if (!attributeMap.isEmpty()) {
	    	user.putPOJO("attributes", attributeMap);
	    }

	    try {
			return mapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private String getStringValue(Object obj, Field idField) {
		try {
			idField.setAccessible(true);
			Object value= idField.get(obj);
			return value == null ? null : value.toString();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private Field findIdField(Object obj) {
		List<Field> idFields = findIdFields(obj.getClass(), new LinkedList<>());

		if (idFields.size() == 1)  {
			return idFields.get(0);
		}
		throw new IllegalArgumentException(
				"Class hierarchy must have a field annotated with @" +  JsonApiId.class.getSimpleName());
	}

	private List<Field> findIdFields(Class<? extends Object> clazz, List<Field> idFields) {
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null) {
			for (Field field: fields) {
				JsonApiId jsonApiId = field.getAnnotation(JsonApiId.class);
				if (jsonApiId != null) {
					JsonApiAttribute jsonApiAttribute = field.getAnnotation(JsonApiAttribute.class);
					if (jsonApiAttribute !=null) {
						throw new IllegalArgumentException(
								"Field annotated with @"  + JsonApiId.class.getSimpleName() +
								" may not be annotated with @" + JsonApiAttribute.class.getSimpleName());
					}

					JsonApiIgnore jsonApiIgnore = field.getAnnotation(JsonApiIgnore.class);
					if (jsonApiIgnore !=null) {
						throw new IllegalArgumentException(
								"Field annotated with @" + JsonApiId.class.getSimpleName() +
								" may not be annotated with @" + JsonApiIgnore.class.getSimpleName());
					}

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
