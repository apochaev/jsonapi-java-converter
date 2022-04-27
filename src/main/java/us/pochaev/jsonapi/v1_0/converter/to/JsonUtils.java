package us.pochaev.jsonapi.v1_0.converter.to;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiParsingException;

public class JsonUtils {

	private final static ObjectMapper mapper = new ObjectMapper();

	public static String toJsonString(Object obj) {
	    try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new JsonApiParsingException(e);
		}
	}
}
