package us.pochaev.jsonapi.v1_0.converter.to.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.JsonApiConverter;

public class ExampleTest {


	@Test
	public void test() {
		String id = "1";
		String name = "John Doe";
		String email = "js@example.com";

		AuthorResource author = new AuthorResource(id, name, email);

		JSONObject json = JsonApiConverter.convert(author);
		assertEquals(1, json.length());

		assertTrue(json.has("data"));

		JSONObject data = json.getJSONObject("data");
		assertEquals(3, data.length());

		assertTrue(data.has("id"));
		assertEquals(id, data.getString("id"));
		assertTrue(data.has("type"));
		assertEquals("author", data.getString("type"));
		assertTrue(data.has("attributes"));

		JSONObject attributes = data.getJSONObject("attributes");
		assertEquals(2, attributes.length());

		assertTrue(attributes.has("name"));
		assertEquals(name, attributes.getString(name));

		assertTrue(attributes.has("email"));
		assertEquals(email, attributes.getString("email"));




		System.out.println(json);
	}
}
