package us.pochaev.jsonapi.v1_0.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import us.pochaev.jsonapi_wip.converter.annotations.JsonApiId;
import us.pochaev.jsonapi_wip.converter.annotations.JsonApiIgnore;

/**
 * Marker annotation that is used to define a class that can be converted to
 * a resource object or resource identifier object as defined by https://jsonapi.org/format/1.0/
 *
 * Default value "" indicates that the simple class name {@link Class#getSimpleName()} is used as the resource object type.
 * Value can be changed to a non empty value.
 *
 * Annotated class must have a public property annotated with {@link JsonApiId},
 * otherwise a {@link RuntimeException} should occur during conversion.
 *
 * All public properties of the class will be converted to resource object attributes,
 * unless the field or getter of the property is annotated with {@link JsonApiIgnore}.
 *
 * Resource object attribute with name @{literal id} is not allowed, therefore
 * the annotated class must not have public property with name {@literal id},
 * unless the property is annotated with {@link JsonApiId} or {@link JsonApiIgnore}.
 * A {@link RuntimeException} should occur during conversion if this condition is not met.
 *
 * Resource object attribute with name @{literal type} is not allowed, therefore
 * the annotated class must not have public property with name {@literal type},
 * unless the property is annotated with {@link JsonApiIgnore}.
 * A {@link RuntimeException} should occur during conversion if this condition is not met.
 *
 * @author a.pochaev@gmail.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonApiObject {
	final static String DEFAULT_VALUE = "${this.getClass().getSimpleName()}";
	/**
	 * Type of the JSON API resource object.
	 * @return type name
	 */
	String value() default DEFAULT_VALUE;

}
