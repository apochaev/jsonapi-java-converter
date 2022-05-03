package us.pochaev.jsonapi.reflection.value;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

import us.pochaev.jsonapi.reflection.exceptions.ValueParsingException;

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
	private final Method getter;


	public ValueDescriptor(String name, Field field) {
		this.name = Objects.requireNonNull(name);
		this.field = Objects.requireNonNull(field);
		getter = null;
	}

	public ValueDescriptor(String name, Field field, Method getter) {
		this.name = Objects.requireNonNull(name);
		this.field = Objects.requireNonNull(field);
		this.getter = Objects.requireNonNull(getter);
	}

	public ValueDescriptor(String name, Member member) {
		this.name = Objects.requireNonNull(name);
		Objects.requireNonNull(member);

		if (member instanceof Field) {
			field = (Field)member;
			getter = null;
		} else if (member instanceof Method) {
			field = null;
			getter = (Method)member;
		} else {
			throw new ValueParsingException("Unsupported member type: " + member.getClass().getName());
		}
	}

	public String getName() {
		return name;
	}

	public Object getValue(Object obj) {
		if (getter != null ) {
			return getValueFromGetter(obj);
		}

		return getValueFromField(obj);
	}

	private Object getValueFromField(Object obj) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException e) {
			throw new ValueParsingException(e);
		} catch (IllegalAccessException e) {
			throw new ValueParsingException(
					obj.getClass().getName() + "#" + field.getName() + " must be public or have a public getter.");
		}
	}

	private Object getValueFromGetter(Object obj) {
		try {
			return getter.invoke(obj, new Object[0]);
		} catch (IllegalAccessException e) {
			throw new ValueParsingException(
					obj.getClass().getName() + "#" + getter.getName() + "() must be public.");
		} catch (IllegalArgumentException | InvocationTargetException e) {
			throw new ValueParsingException(e);
		}
	}

	@Override
	public String toString() {
		return "ValueDescriptor [name=" + name + ", field=" + field + ", getter=" + getter + "]";
	}


	public <T extends Annotation> Optional<T> getOptionalAnnotation(Class<T> annotationClass) {
		if (getter != null) {
			T annotation = getter.getAnnotation(annotationClass);
			if (annotation != null) {
				return Optional.of(annotation);
			}
		}

		if (field != null) {
			T annotation = field.getAnnotation(annotationClass);
			if (annotation != null) {
				return Optional.of(annotation);
			}
		}

		return Optional.empty();
	}
}
