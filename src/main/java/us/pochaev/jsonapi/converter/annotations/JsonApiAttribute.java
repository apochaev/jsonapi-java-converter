package us.pochaev.jsonapi.converter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface JsonApiAttribute {
	/**
	 * Json Api attribute key.
	 * @return attribute key
	 */
	String value();
}
