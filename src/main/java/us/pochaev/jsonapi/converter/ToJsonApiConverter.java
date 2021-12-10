package us.pochaev.jsonapi.converter;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import us.pochaev.jsonapi.converter.annotations.JsonApiId;
import us.pochaev.jsonapi.converter.annotations.JsonApiObject;

class ToJsonApiConverter {
	private final static ObjectMapper mapper = new ObjectMapper();

	public String convert(Object obj) {
		Objects.requireNonNull(obj);

		String type = getType(obj);


		Field idField = findIdField(obj);
		String id = getStringValue(obj, idField);

//		for ()
		return createJsonApiObjectString(id, type);
	}

	private String getType(Object obj) {
		JsonApiObject jsonApiObject = obj.getClass().getAnnotation(JsonApiObject.class);
		if (jsonApiObject != null) {
			String type = jsonApiObject.value();
			if (type != null && type.trim().length() > 0) {
				return type;
			}
			throw new IllegalArgumentException("@JsonObject value may not be blank");
		}
		throw new IllegalArgumentException("Class must be annotated with @JsonObject");
	}

	private String createJsonApiObjectString(String id, String type) {
	    ObjectNode user = mapper.createObjectNode();
	    user.put("id", id);
	    user.put("type", type);

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
		Optional<Field> id = Optional.empty();

		Field[] fields = obj.getClass().getDeclaredFields();
		if (fields != null) {

			for (Field field: fields) {
				JsonApiId jsonApiId = field.getAnnotation(JsonApiId.class);
				if (jsonApiId != null) {
					if (id.isPresent()) {
						throw new IllegalArgumentException(
								"Class must have a single field annotated with @JsonApiId");
					}
					id = Optional.of(field);
				}
			}
		}

		return id
				.orElseThrow(() ->
					new IllegalArgumentException(
							"Class must have a single field annotated with @JsonApiId"));
	}



}
