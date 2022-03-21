package us.pochaev.jsonapi_wip.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private final static ObjectMapper mapper = new ObjectMapper();

	public static String toJsonString(Object obj) {
	    try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
