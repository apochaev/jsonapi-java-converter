package us.pochaev.jsonapi.v1_0.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation that is used to define a non-static class member that will be used as a resource object {@literal id}.
 *
 * Annotated member must be either a public field, a non-public field with a public getter named in accordance with
 * Java Bean specification or a public method with no parameters.
 *
 * If an annotated field has a getter, field value will be retrieved using the getter, whether the field is accessible or not.
 *
 * @author a.pochaev@gmail.com
 *
 * TODO update the JavaDoc once the functionality is locked in.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface JsonApiId {

}
