package us.pochaev.jsonapi.v1_0.converter.to.example;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject("author")
public class AuthorResource extends AbstractJsonApiObject {

	@JsonApiAttribute
	private final String name;

	@JsonApiAttribute
	private final String email;

	public AuthorResource(String name, String email ) {
		super(null);
		this.name = name;
		this.email = email;
	}

	public AuthorResource(String id, String name, String email ) {
		super(id);
		this.name = name;
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public String getEmail() {
		return email;
	}

}
