package us.pochaev.jsonapi.converter.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author apochaev //TODO need an email here
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonApiObject {
	/**
	 * Defines handling of fields not annotated with {@link JsonApiAttribute} or {@link JsonApiIgnore}
	 */
	public enum UnmarkedFields {
		/**
		 * Include only fields annotated with {@link JsonApiAttribute} and {@link JsonApiId}
		 */
		None,
		/**
		 * Include public fields and fields annotated with {@link JsonApiAttribute}
		 */
		Public,
		/**
		 * Include all fields not annotated with {@link JsonApiIgnore}
		 */
		All,
	}

	UnmarkedFields includeUnmarkedFields() default UnmarkedFields.All; //TODO need a better name

	/**
	 * Type of the JsonApi object
	 * @return
	 */
	String value();

}
