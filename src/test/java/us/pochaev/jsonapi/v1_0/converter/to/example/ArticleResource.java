package us.pochaev.jsonapi.v1_0.converter.to.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import us.pochaev.jsonapi.v1_0.annotations.JsonApiAttribute;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiObject;

@JsonApiObject("article")
public class ArticleResource extends AbstractJsonApiObject {


	private final String title;

	@JsonApiAttribute("content")
	private final String data;

	private final Collection<String> tags;

	@JsonApiIgnore
	private final String ignored = "ignored";

	public ArticleResource(String title, String content) {
		super(null);
		this.title = title;
		data = content;
		tags = Collections.emptyList();
	}

	public ArticleResource(String title, String content, Collection<String> tags) {
		super(null);
		this.title = title;
		data = content;
		this.tags = new ArrayList<>(tags);
	}

	public String getTitle() { // JsonApiAttribute as a public getter on @JsonApiObject
		return title;
	}

	public String getData() { // JsonApiAttribute 'content' per @JsonApiAttribute annotation on the field
		return data;
	}

	public Collection<String> getTags() { // JsonApiAttribute as a public getter on @JsonApiObject
		return tags;
	}

	public String getIgnored() {
		return ignored;
	}



}
