package us.pochaev.jsonapi.v1_0.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(CLASS)
@Target({ FIELD, METHOD })
public @interface JsonApiMetaItem {
	/**
	 * Json Api Meta item key. If omitted, property name is used.
	 * @return Meta item key key
	 */
	String value() default "";
}
