package us.pochaev.jsonapi.reflection;

import java.lang.reflect.Method;
import java.util.Objects;

public class Getter {
	private final String name;
	private final Method method;

	Getter(String name, Method method) {
		this.name = Objects.requireNonNull(name); // MAYBE require non blank?
		this.method = Objects.requireNonNull(method);
	}

	public String getName() {
		return name;
	}

	public Method getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return "Getter [name=" + name + ", method=" + method + "]";
	}

}
