package us.pochaev.jsonapi.v1_0.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface JsonApiAttribute {

	public static final String DEFAULT_VALUE = "";

	/**
	 * Json Api attribute key.
	 * @return attribute key
	 */
	String value() default DEFAULT_VALUE;
}
