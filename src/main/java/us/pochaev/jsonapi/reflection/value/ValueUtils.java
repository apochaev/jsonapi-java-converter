package us.pochaev.jsonapi.reflection.value;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import us.pochaev.jsonapi.reflection.ancestry.AncestryUtils;
import us.pochaev.jsonapi.reflection.property.PropertyNameUtils;
import us.pochaev.jsonapi.v1_0.annotations.JsonApiIgnore;

public class ValueUtils {

	/**
	 * Find all annotated fields find a matching public getter if no getter and not declared field drop it.
	 * @param includeAnnotationClasses include only value descriptors annotated with ANY annotation in the array.
	 * If the array is null, include any value descriptors.
	 * If the array is empty include none, since no annotations are specified.
	 * @param excludeAnnotationClasses exclude value descriptors annotated with ANY annotation in the array.
	 * If the array is null, exclude none value descriptors.
	 * If the array is empty exclude none, since no annotations are specified.
	 *
	 */
	public static Map<String, ValueDescriptor> getValueDescriptors(
			Class<? extends Annotation>[] includeAnnotationClasses,
			Class<? extends Annotation>[] excludeAnnotationClasses,
			Object object) {

		Stack<Class<?>> objectAncestry = AncestryUtils.getAncestry(object);

		Map<String, ValueDescriptor> valueDescriptorMap = new HashMap<>();

		while (!objectAncestry.isEmpty()) {
			Class<?> cls = objectAncestry.pop();
			Field[] fields = cls.getDeclaredFields();
			Method[] declaredMethods = cls.getDeclaredMethods();
			List<Field> valueFields = findAnnotatedFields(
					includeAnnotationClasses,
					excludeAnnotationClasses,
					fields);

			for (Field field : valueFields) {
				if (isConcreteInstanceMember(field)) {
					String name = field.getName();
					ValueDescriptor vd;
					Optional<Method> optionalGetter = findGetter(field, declaredMethods);
					if (optionalGetter.isPresent()) {
						Method getter = optionalGetter.get();
						vd = new ValueDescriptor(name, field, getter);
					} else {
						vd = new ValueDescriptor(name, field);
					}

					valueDescriptorMap.put(name, vd);
				}
			}

			Map<String, Method> valueGetters= findAnnotatedGetters(
					includeAnnotationClasses,
					excludeAnnotationClasses,
					declaredMethods);

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
	private static List<Field> findAnnotatedFields(
			Class<? extends Annotation>[] includeAnnotationClasses,
			Class<? extends Annotation>[] excludeAnnotationClasses,
			Field[] fields) {
		return Arrays
			.stream(fields)
			.filter(field -> ! field.isSynthetic())
			.filter(field -> ! Modifier.isStatic(field.getModifiers()))
			.filter(field -> isIncluded(includeAnnotationClasses, excludeAnnotationClasses, field))
			.collect(Collectors.toList());
	}

	private static boolean isIncluded(
			Class<? extends Annotation>[] includeAnnotationClasses,
			Class<? extends Annotation>[] excludeAnnotationClasses,
			AccessibleObject member) {
		boolean result = true; //include all by default

		if ( includeAnnotationClasses != null ) {
			//include only specified
			result = false;
			List<Class<? extends Annotation>> includeList = Arrays.asList(includeAnnotationClasses);
			for (Annotation annotation: member.getAnnotations()) {
				if (includeList.contains(annotation.annotationType())) {
					result = true;
					break;
				}
			}
		}

		if ( excludeAnnotationClasses != null ) {
			//exclude specified even if previously included
			List<Class<? extends Annotation>> excludeList = Arrays.asList(excludeAnnotationClasses);
			for (Annotation annotation: member.getAnnotations()) {
				if (excludeList.contains(annotation.annotationType())) {
					result = false;
					break;
				}
			}
		}

		return result;
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
	private static Map<String, Method> findAnnotatedGetters(
			Class<? extends Annotation>[] includeAnnotationClasses,
			Class<? extends Annotation>[] excludeAnnotationClasses,
			Method[] methods) {
		List<Method> methodShortList = Arrays
			.stream(methods)
				.filter(method -> ! method.isSynthetic())
				.filter(method -> ! Modifier.isStatic(method.getModifiers()))
				.filter(method -> ( method.getParameterCount() == 0))
				.filter(method -> isIncluded(includeAnnotationClasses, excludeAnnotationClasses, method))
				.filter(method -> ! isJdkMethod(method))
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

	private static boolean isJdkMethod(Method method) {
		return method.getName().equals("getClass") &&
				method.getParameterCount() == 0 &&
				method.getReturnType().equals(Class.class);
	}

	private static boolean isConcreteInstanceMember(Member member) {
		int modifiers = member.getModifiers();
		return !Modifier.isStatic(modifiers) &&
			   !Modifier.isAbstract(modifiers);
	}

}
