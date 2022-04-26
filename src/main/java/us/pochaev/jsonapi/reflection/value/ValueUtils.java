package us.pochaev.jsonapi.reflection.value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import us.pochaev.jsonapi.reflection.ancestry.AncestryUtils;
import us.pochaev.jsonapi.reflection.property.PropertyNameUtils;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;

public class ValueUtils {

	/**
	 * Find all annotated fields find a matching public getter if no getter and not declared field drop it.
	 *
	 */
	public static Map<String, ValueDescriptor> getValueDescriptors(Class<? extends Annotation> annotationClass, Object object) {
		Stack<Class<?>> objectAncestry = AncestryUtils.getAncestry(object);

		Map<String, ValueDescriptor> valueDescriptorMap = new HashMap<>();

		while (!objectAncestry.isEmpty()) {
			Class<?> cls = objectAncestry.pop();
			Field[] fields = cls.getDeclaredFields();
			Method[] declaredMethods = cls.getDeclaredMethods();
			List<Field> valueFields = findAnnotatedFields(annotationClass, fields);
			Collection<Method> knownGetters = new LinkedList<>();
			for (Field field : valueFields) {
				if (isConcreteInstanceMember(field)) {
					String name = field.getName();
					ValueDescriptor vd;
					Optional<Method> optionalGetter = findGetter(field, declaredMethods);
					if (optionalGetter.isPresent()) {
						Method getter = optionalGetter.get();
						knownGetters.add(getter);
						vd = new ValueDescriptor(name, field, getter);
					} else {
						vd = new ValueDescriptor(name, field);
					}

					valueDescriptorMap.put(name, vd);
				}
			}

			//TODO refactor all getters in one place, change value descriptor to hold accessor only

			Map<String, Method> valueGetters= findAnnotatedGetters(annotationClass, declaredMethods);
			for (String propertyName : valueGetters.keySet()) {
				valueDescriptorMap.put(
						propertyName,
						new ValueDescriptor(propertyName, valueGetters.get(propertyName)));
			}
		}

		return valueDescriptorMap;
	}


	/**
	 * Returns non-static public methods of the objectClass that match getter name convention for the provided field names.
	 * @param keySet
	 * @param objectClass
	 *
	 * TODO optimize so that getDeclaredMethods is called once per class not per field
	 */
	private static Optional<Method> findGetter(Field field, Method[] declaredMethods) {
		for (Method method : declaredMethods) {
			if (method.getParameterCount() == 0) {
				if (method.getReturnType().equals(field.getType())) {
					String getterName = PropertyNameUtils.createGetterName(field);
					if (method.getName().equals(getterName)) {
						return Optional.of(method);
					}
				}
			}
		}

		return Optional.empty();
	}

	/**
	 * Returns non-synthetic non-static fields annotated with the given annotation class and
	 * not annotated with {@link JsonApiIgnore}, contained in the fields array.
	 *
	 * @param annotationClass Annotation class, must not be null.
	 * @param fields Field array, must not be null.
	 * @return Fields matching the criteria
	 */
	protected static List<Field> findAnnotatedFields(Class<? extends Annotation> annotationClass, Field[] fields) {
		Objects.requireNonNull(annotationClass);
		return Arrays
			.stream(fields)
			.filter(field -> ! field.isSynthetic())
			.filter(field -> ! Modifier.isStatic(field.getModifiers()))
			.filter(field -> (field.getAnnotation(JsonApiIgnore.class) == null))
			.filter(field -> (field.getAnnotation(annotationClass) != null))
			.collect(Collectors.toList());
	}

	/**
	 * Returns public non-synthetic, non-static methods following getter naming convention and
	 * annotated with the given annotation class and
	 * not annotated with {@link JsonApiIgnore}, contained in the fields array.
	 *
	 * @param annotationClass Annotation class, must not be null.
	 * @param methods Method array, must not be null.
	 * @return Methods matching the criteria
	 */
	private static Map<String, Method> findAnnotatedGetters(Class<? extends Annotation> annotationClass, Method[] methods) {
		Objects.requireNonNull(annotationClass);
		List<Method> methodShortList = Arrays
			.stream(methods)
				.filter(method -> ! method.isSynthetic())
				.filter(method -> ! Modifier.isStatic(method.getModifiers()))
				.filter(method -> ( method.getAnnotation(JsonApiIgnore.class) == null))
				.filter(method -> ( method.getAnnotation(annotationClass) != null))
				.filter(method -> ( method.getParameterCount() == 0))
			.collect(Collectors.toList());

		Map<String, Method> results = new HashMap<>();
		for (Method method: methodShortList) {
			Optional<String> optionalPropertyName = PropertyNameUtils.getOptionalPropertyName(method.getName());
			if (optionalPropertyName.isPresent()) {
				results.put(optionalPropertyName.get(), method);
			}
		}

		return results;


	}

	protected static boolean isConcreteInstanceMember(Member member) {
		int modifiers = member.getModifiers();
		return !Modifier.isStatic(modifiers) &&
			   !Modifier.isAbstract(modifiers);
	}




}
