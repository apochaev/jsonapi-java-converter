package us.pochaev.jsonapi.reflection;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * Similar to {@link PropertyDescriptor}
 * No synthetic or static fields or methods, no abstract classes, no abstract methods.
 *
 * @author a.pochaev@gmail.com
 *
 * TODO update JavaDoc
 */
public class ValueDescriptor {

	private final Field field;
	private final Optional<Method> optionalGetter;


	public ValueDescriptor(Field field) {
		this.field = Objects.requireNonNull(field);
		optionalGetter = Optional.empty();
	}

	public ValueDescriptor(Field field, Method getter) {
		this.field = Objects.requireNonNull(field);
		optionalGetter = Optional.of(getter);
	}

	public ValueDescriptor(Field field, Optional<Method> optionalGetter) {
		this.field = field;
		this.optionalGetter = optionalGetter;
	}

	public Object getValue(Object obj) {
		if (optionalGetter.isPresent()) {
			return getValueFromGetter(obj);
		}

		return getValueFromField(obj);
	}

	protected Object getValueFromField(Object obj) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	protected Object getValueFromGetter(Object obj) {
		try {
			return optionalGetter.get().invoke(obj, new Object[0]);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

}
