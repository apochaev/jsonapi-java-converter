package us.pochaev.jsonapi.v1_0.converter.to.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.JsonApiConverter;

public class ExampleTest {

	@Test
	public void testOne() {
		AuthorResource author = new AuthorResource("1", "John Doe", "john.doe@example.com");

		JSONObject json = JsonApiConverter.convert(author);
		assertEquals(1, json.length());

		System.out.println(json);

		assertTrue(json.has("data"));

		JSONObject data = json.getJSONObject("data");
		assertEquals(3, data.length());

		assertTrue(data.has("id"));
		assertEquals(author.getId(), data.getString("id"));
		assertTrue(data.has("type"));
		assertEquals("author", data.getString("type"));
		assertTrue(data.has("attributes"));

		JSONObject attributes = data.getJSONObject("attributes");
		assertEquals(2, attributes.length());

		assertTrue(attributes.has("name"));
		assertEquals(author.getName(), attributes.getString("name"));

		assertTrue(attributes.has("email"));
		assertEquals(author.getEmail(), attributes.getString("email"));
	}

	@Test
	public void testMany() {
		AuthorResource author1 = new AuthorResource("1", "John Doe", "john.doe@example.com");
		AuthorResource author2 = new AuthorResource("2", "Jane Doe", "jane.doe@example.com");

		JSONArary json = JsonApiConverter.convert(Set.of(author1, author2));
		assertEquals(1, json.length());

		System.out.println(json);

		assertTrue(json.has("data"));

		JSONObject data = json.getJSONObject("data");
		assertEquals(3, data.length());

		assertTrue(data.has("id"));
		assertEquals(id1, data.getString("id"));
		assertTrue(data.has("type"));
		assertEquals("author", data.getString("type"));
		assertTrue(data.has("attributes"));

		JSONObject attributes = data.getJSONObject("attributes");
		assertEquals(2, attributes.length());

		assertTrue(attributes.has("name"));
		assertEquals(name1, attributes.getString("name"));

		assertTrue(attributes.has("email"));
		assertEquals(email1, attributes.getString("email"));
	}
}
