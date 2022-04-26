package us.pochaev.jsonapi.reflection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ReflectionUtilsTest {

	private static final int FIELD_COUNT_PUBLIC = 1;

	@Test @DisplayName("WHEN getAnnotatedFields none THEN empty collection")
	void none() {
		Object obj = new TestNone();
		Collection<Field> annotatedFields = ReflectionUtils.getAnnotatedInstanceFields(TestMemberAnnotation.class, obj.getClass());
		assertTrue(annotatedFields.isEmpty());
	}

	@Test @DisplayName("WHEN getAnnotatedFields most THEN non empty collection")
	void test2() {
		Object obj = new TestMost();
		Collection<Field> annotatedFields = ReflectionUtils.getAnnotatedInstanceFields(TestMemberAnnotation.class, obj.getClass());
		assertEquals(FIELD_COUNT_PUBLIC, annotatedFields.size());

		List<String> fieldNames = annotatedFields.stream()
				.map(field -> field.getName())
				.collect(Collectors.toList());
		assertThat(fieldNames, hasItems(
				"publicField"));
	}

	@Test @DisplayName("WHEN getAnnotatedFields none child none parent THEN empty collection")
	void noneChildnoneParent() {
		Object obj = new TestNoneChild();
		Collection<Field> annotatedFields = ReflectionUtils.getAnnotatedInstanceFields(TestMemberAnnotation.class, obj.getClass());
		assertTrue(annotatedFields.isEmpty());
	}

	@Test @DisplayName("WHEN getAnnotatedFields most parent none child THEN non empty collection")
	void mostParentNoneChild() {
		Object obj = new TestMostParentNoneChild();
		Collection<Field> annotatedFields = ReflectionUtils.getAnnotatedInstanceFields(TestMemberAnnotation.class, obj.getClass());
		assertEquals(FIELD_COUNT_PUBLIC, annotatedFields.size());

		List<String> fieldNames = annotatedFields.stream()
				.map(field -> field.getName())
				.collect(Collectors.toList());

		assertThat(fieldNames, hasItems(
				"publicField"));
	}

	@Test @DisplayName("WHEN getAnnotatedFields most parent most child THEN non empty collection")
	void mostParentMostChild() {
		Object obj = new TestMostParentMostChild();
		Collection<Field> annotatedFields = ReflectionUtils.getAnnotatedInstanceFields(TestMemberAnnotation.class, obj.getClass());
		assertEquals(FIELD_COUNT_PUBLIC, annotatedFields.size());

		List<String> fieldNames = annotatedFields.stream()
				.map(field -> field.getName())
				.collect(Collectors.toList());

		assertThat(fieldNames, hasItems(
				"publicField"));
	}

	@Test @DisplayName("WHEN getAnnotatedFields most parent most child THEN child public field only")
	void mostParentMostChildPublicField() throws IllegalArgumentException, IllegalAccessException {
		TestMostParentMostChild obj = new TestMostParentMostChild();
		Collection<Field> annotatedFields = ReflectionUtils.getAnnotatedInstanceFields(TestMemberAnnotation.class, obj.getClass());
		assertEquals(1 , annotatedFields.size());

		List<Object> publicFieldValues = annotatedFields
				.stream()
				.filter(field -> (field.getName().equals("publicField")))
				.map(field -> getValue(field, obj))
				.collect(Collectors.toList());

		assertEquals(1, publicFieldValues.size());
		assertThat(publicFieldValues, hasItems(
				"publicFieldChild"));


		assertEquals("publicFieldChild", obj.publicField);
		assertEquals("publicFieldChild", obj.getPublicField());
	}

	@Test @DisplayName("WHEN getAnnotatedFields most parent most child most grand child THEN grand child public field only")
	void mostParentMostChildMostGrandChildPublicField() throws IllegalArgumentException, IllegalAccessException {
		TestMostParentMostChild obj = new TestMostParentMostChildMostGrandChild();
		Collection<Field> annotatedFields = ReflectionUtils.getAnnotatedInstanceFields(TestMemberAnnotation.class, obj.getClass());
		assertEquals(1 , annotatedFields.size());

		List<Object> publicFieldValues = annotatedFields
				.stream()
				.filter(field -> (field.getName().equals("publicField")))
				.map(field -> getValue(field, obj))
				.collect(Collectors.toList());

		assertEquals(1, publicFieldValues.size());
		assertThat(publicFieldValues, hasItems(
				"publicFieldGrandChild"));


		assertEquals("publicFieldGrandChild", obj.publicField);
		assertEquals("publicFieldGrandChild", obj.getPublicField());
	}

	protected Object getValue(Field field, TestMostParentMostChild obj) {
		try {
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Test @DisplayName("WHEN parentFirst THEN parent is first")
	void whenParentFirstThenParentIsFirst() {
		assertEquals(0, ReflectionUtils.parentFirst(Object.class, Object.class, Object.class));
		assertEquals(1, ReflectionUtils.parentFirst(Object.class, Boolean.class, Object.class));
		assertEquals(-1, ReflectionUtils.parentFirst(Boolean.class, Object.class, Object.class));
		assertEquals(0, ReflectionUtils.parentFirst(Boolean.class, Integer.class, Object.class));
	}
}
