package us.pochaev.jsonapi_wip.converter.to;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import us.pochaev.jsonapi_wip.converter.annotations.JsonApiIgnore;

public class ToJsonApiConverter {

	private final static ObjectMapper mapper = new ObjectMapper();

	public static String convert(Object obj) {
		Objects.requireNonNull(obj);

		String type = JsonApiObjectParser.parseType(obj);
		String id = null;
		Map<String, Object> attributeMap = null;

		/*
		Collection<Field> fields = JsonApiIdFieldFinder.findFields(obj.getClass());


		Field idField = JsonApiIdFieldFinder.findIdField(obj);
		String id = getStringValue(obj, idField);

		Map<String, Field> fieldMap = findAttributeFields(obj.getClass(), new HashMap<>());
		Map<String, Object> attributeMap = getFieldValues(obj, fieldMap);
*/
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
				if (field.isSynthetic()) {
					continue;
				}

				JsonApiIgnore jsonApiIgnore = field.getAnnotation(JsonApiIgnore.class);
				if (jsonApiIgnore == null) {
					if (fieldMap.containsKey( field.getName())) {
						continue; //skip parent field overwritten in child;
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


	private static String createJsonApiObjectString(String id, String type, Map<String, Object> attributeMap) {
	    ObjectNode user = mapper.createObjectNode();
	    user.put("id", id);
	    user.put("type", type);

	    if (attributeMap != null && attributeMap.size() > 0) {
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





}
