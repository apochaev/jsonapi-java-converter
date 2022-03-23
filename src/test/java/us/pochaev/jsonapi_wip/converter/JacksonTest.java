package us.pochaev.jsonapi_wip.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;

import us.pochaev.jsonapi.v1_0.converter.to.id_parser.Constants;

public class JacksonTest {
	static class PrivateField {
		private Object id  = Constants.ID;
	}

	static class PrivateAnnotatedField {
		@JsonProperty private Object id  = Constants.ID;
	}

	static class PrivateAnnotatedFieldPublicGetter {
		@JsonProperty private Object id  = Constants.ID;
		public Object getId() {
			return id;
		}
	}

	static class PrivateFieldPublicAccessor {
		@JsonProperty private Object id  = Constants.ID;
		@JsonProperty public Object getjsonApiId() {
			return id;
		}
	}

	@Test
	public void testPrivateField() {
		Object obj = new PrivateField();
		assertThrows(RuntimeException.class, () ->
			JsonUtils.toJsonString(obj));

	}

	@Test
	public void testPrivateAnnotatedField() {
		Object obj = new PrivateAnnotatedField();
		String jsonString = JsonUtils.toJsonString(obj);
		assertEquals("{\"id\":\"id\"}", jsonString);
	}

	@Test
	public void testPrivateAnnotatedFieldPublicGetter() {
		Object obj = new PrivateAnnotatedFieldPublicGetter();
		String jsonString = JsonUtils.toJsonString(obj);
		assertEquals("{\"id\":\"id\"}", jsonString);
//		System.out.println(jsonString);
	}











	@Test
	public void testX() {
		Object obj = new PrivateField();
		String jsonString = JsonUtils.toJsonString(obj);
		System.out.println(jsonString);
	}
}
