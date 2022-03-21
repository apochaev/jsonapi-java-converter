package us.pochaev.jsonapi_wip.converter.parser;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JsonApiIdPropertyFinder {

	public static Optional<String> findIdPropertyName(Class<?> cls) {
		String idPropertyName = findIdPropertyName(cls, new LinkedList<>());
		return Optional.ofNullable(idPropertyName);
	}

	static String findIdPropertyName(Class<?> cls, Collection<Field> fields) {

		 BeanInfo beanInfo;
			try {
				beanInfo = Introspector.getBeanInfo(cls);
			} catch (IntrospectionException e) {
				throw new RuntimeException(e);
			}

		    PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

		    for (PropertyDescriptor pd : pds) {
		      String propertyName = pd.getName();
		      System.out.println("propertyName = " + propertyName);
		    }

		   return null;
	}

	public static String findIdProperty(Class<?> cls, List<Field> idFields) {


/*
		Field[] fields = cls.getDeclaredFields();
		if (fields != null) {
			for (Field field: fields) {
				if (field.isSynthetic()) {
					continue;
				}

				JsonApiId jsonApiId = field.getAnnotation(JsonApiId.class);
				if (jsonApiId != null) {
					JsonApiAttribute jsonApiAttribute = field.getAnnotation(JsonApiAttribute.class);
					if (jsonApiAttribute !=null) {
						throw new IllegalArgumentException(
								"Field annotated with @"  + JsonApiId.class.getSimpleName() +
								" may not be annotated with @" + JsonApiAttribute.class.getSimpleName());
					}

					JsonApiIgnore jsonApiIgnore = field.getAnnotation(JsonApiIgnore.class);
					if (jsonApiIgnore !=null) {
						throw new IllegalArgumentException(
								"Field annotated with @" + JsonApiId.class.getSimpleName() +
								" may not be annotated with @" + JsonApiIgnore.class.getSimpleName());
					}

					if (idFields.size() > 0) {
						throw new IllegalArgumentException(
								"Class hierarchy must have a single field annotated with @" + JsonApiId.class.getSimpleName());
					}
					idFields.add(field);
				}
			}
		}

		if (clazz.getSuperclass() != null) {
			return findIdFields(clazz.getSuperclass(), idFields);
		}

		return idFields;*/
		return null;
	}

	/*
	public static Pair Field findIdField(Object obj) {
		List<Field> idFields = findIdFields(obj.getClass(), new LinkedList<>());

		if (idFields.size() == 1)  {
			return idFields.get(0);
		}
		throw new IllegalArgumentException(
				"Class hierarchy must have a field annotated with @" +  JsonApiId.class.getSimpleName());
	}
*/
}
