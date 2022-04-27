package us.pochaev.jsonapi.reflection.property;

import java.lang.reflect.Field;
import java.util.Optional;

public class PropertyNameUtils {
	private static final String PREFIX_IS = "is";
	private static final String PREFIX_GET = "get";

	public static Optional<String> getOptionalPropertyName(String name) {
		if (name.startsWith(PREFIX_GET)) {
			return getOptionalPropertyName(PREFIX_GET, name);
		} else if (name.startsWith(PREFIX_IS)) {
			return getOptionalPropertyName(PREFIX_IS, name);
		}

		return Optional.empty();
	}

	private static Optional<String> getOptionalPropertyName(String prefix, String name) {
		String property = name.substring(prefix.length());
		if (property.length() > 0 && Character.isUpperCase(property.charAt(0))) {
			return Optional.of(decapitalize(property));
		}
		return Optional.empty();
	}

	static String decapitalize(String string) {
		return string.substring(0,1).toLowerCase() + string.substring(1);
	}

	public static String createGetterName(Field field) {
		if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
			return PREFIX_IS + capitalize(field.getName());
		}
		return PREFIX_GET + capitalize(field.getName());
	}

	static String capitalize(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1);
	}
}
