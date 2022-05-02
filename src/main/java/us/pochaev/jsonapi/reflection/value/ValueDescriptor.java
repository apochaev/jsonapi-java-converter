package us.pochaev.jsonapi.reflection.value;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiParsingException;

/**
 * Similar to {@link PropertyDescriptor}
 * No synthetic or static fields or methods, no abstract classes, no abstract methods.
 *
 * @author a.pochaev@gmail.com
 *
 * TODO update JavaDoc
 */
public class ValueDescriptor {
	private final String name;
	private final Field field;
	private final Optional<Method> optionalGetter;


	public ValueDescriptor(String name, Field field) {
		this.name = Objects.requireNonNull(name);
		this.field = Objects.requireNonNull(field);
		optionalGetter = Optional.empty();
	}

	public ValueDescriptor(String name, Field field, Method getter) {
		this.name = Objects.requireNonNull(name);
		this.field = Objects.requireNonNull(field);
		optionalGetter = Optional.of(getter);
	}

	public ValueDescriptor(String name, Member member) {
		this.name = Objects.requireNonNull(name);
		Objects.requireNonNull(member);

		if (member instanceof Field) {
			field = (Field)member;
			optionalGetter = Optional.empty();
		} else if (member instanceof Method) {
			field = null;
			Method method = (Method)member;
			optionalGetter = Optional.of(method);
		} else {
			throw new JsonApiParsingException("Unsupported member type: " + member.getClass().getName());
		}
	}

	public String getName() {
		return name;
	}

	public Object getValue(Object obj) {
		if (optionalGetter.isPresent()) {
			return getValueFromGetter(obj);
		}

		return getValueFromField(obj);
	}

	private Object getValueFromField(Object obj) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			throw new JsonApiParsingException(e);
		} catch (IllegalAccessException e) {
			throw new JsonApiParsingException(
					obj.getClass().getName() + "#" + field.getName() + " must be public or have a public getter.");
		}
	}

	private Object getValueFromGetter(Object obj) {
		Method getter = optionalGetter.get();
		try {
			return getter.invoke(obj, new Object[0]);
		} catch (IllegalAccessException e) {
			throw new JsonApiParsingException(
					obj.getClass().getName() + "#" + getter.getName() + "() must be public.");
		} catch (IllegalArgumentException | InvocationTargetException e) {
			throw new JsonApiParsingException(e);
		}
	}

	@Override
	public String toString() {
		return "ValueDescriptor [name=" + name + ", field=" + field + ", optionalGetter=" + optionalGetter + "]";
	}


	public <T extends Annotation> Optional<T> getOptionalAnnotation(Class<T> annotationClass) {
		if (optionalGetter.isPresent()) {
			Method method = optionalGetter.get();
			T annotation = method.getAnnotation(annotationClass);
			if (annotation != null) {
				return Optional.of(annotation);
			}
		}

		T annotation = field.getAnnotation(annotationClass);
		if (annotation != null) {
			return Optional.of(annotation);
		}

		return Optional.empty();
	}
}
