package us.pochaev.jsonapi.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;


public final class ReflectionUtils {

	/**
	 * Returns an array containing Field objects reflecting all
	 * the accessible public non static fields of the class or interface represented by this Class object
	 * that are annotated with a given annotation.
	 *
	 * Note that if both parent and child classes have a public field of the same name both field
	 * objects are available via reflection. The result of this operation will NOT include public fields
	 * of a parent class  should it have the same name as a field in this class.
	 *
	 * @param annotationClass  the Class object corresponding to the annotation type
	 * @param cls the Class object to introspect
	 * @return the collection of Field objects representing the annotated public fields
	 */
	public static Collection<Field> getAnnotatedInstanceFields(Class<? extends Annotation> annotationClass, Class<? extends Object> cls) {

		return Arrays
				.stream(cls.getFields())
				.filter(field -> ! Modifier.isStatic(field.getModifiers()))
				.filter(field -> (field.getAnnotation(annotationClass) != null))
				.sorted((field1, field2) -> parentFirst(field1.getDeclaringClass(), field2.getDeclaringClass(), cls))
				.collect(Collectors.toMap(Field::getName, Function.identity(), (parent, child) -> child))
				.values();
	}

	static int parentFirst(Class<?> cls1, Class<?> cls2, Class<? extends Object> cls) {

		if (cls1.equals(cls) && !cls2.equals(cls)) {
			return 1;
		}

		if (!cls1.equals(cls) && cls2.equals(cls)) {
			return -1;
		}

		return 0;
	}



}