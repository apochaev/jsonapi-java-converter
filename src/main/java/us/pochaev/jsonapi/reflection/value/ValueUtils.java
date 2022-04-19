package us.pochaev.jsonapi.reflection.value;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import us.pochaev.jsonapi.reflection.ancestry.AncestryUtils;
import us.pochaev.jsonapi.reflection.property.PropertyNameUtils;

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

//			List<Method> valueMethods= findAnnotatedMethods(annotationClass, declaredMethods);

			//find annotated methods skip any that are in existing value descriptors, create value descriptors
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
	 * Returns non-synthetic non-static fields annotated with the given annotation class contained in the fields array.
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
			.filter(field -> (field.getAnnotation(annotationClass) != null))
			.collect(Collectors.toList());
	}

	/**
	 * Returns public non-synthetic, non-static methods following getter naming convention and annotated with the given annotation class contained in the fields array.
	 *
	 * @param annotationClass Annotation class, must not be null.
	 * @param methods Method array, must not be null.
	 * @return Methods matching the criteria
	 */
	private static List<Method> findAnnotatedGetters(Class<? extends Annotation> annotationClass, Method[] methods) {
		Objects.requireNonNull(annotationClass);
//		return Arrays
//				.stream(methods)
//				.filter(method -> ! method.isSynthetic())
//				.filter(method -> ! Modifier.isStatic(method.getModifiers()))
//				.filter(method -> ( method.getAnnotation(annotationClass) != null))
//				.filter(method -> ( method.getParameterCount() == 0))
//				.filter(method -> ( isGetterLikeName(method.getName()));
//
				return Collections.emptyList();


	}



	/**
	 * Returns non-static public methods of the objectClass that match getter name convention for the provided field names.
	 * @param keySet
	 * @param objectClass
	 */
	private static void findValueGetters(Set<String> fieldNames, Class<?> objectClass) {
//		return Arrays
//				.stream(objectClass.getme())

	}




//	?Optional<Method> optionalGetter = findGetter(field, cls);
	/**
	 * Returns a concrete instance accessible getter
	 * @param field
	 * @param cls
	 * @return
	 * TODO update JavaDoc
	 */
	private static Optional<Method> findGetter(Collection<String> names, Class<? extends Object> cls) {
//		for (Method method : cls.getMethods()) {
//			String getterName = createGetterName(field);
//			if (method.getName().equals(getterName)) {
//
//			}
//
//			if (isConcreteInstanceMember(method)) {
//				if (method.isAccessible()) {
//
//				}
//			}
//		}

		// TODO Auto-generated method stub
		return null;
	}

	protected static boolean isConcreteInstanceMember(Member member) {
		int modifiers = member.getModifiers();
		return !Modifier.isStatic(modifiers) &&
			   !Modifier.isAbstract(modifiers);
	}




}
