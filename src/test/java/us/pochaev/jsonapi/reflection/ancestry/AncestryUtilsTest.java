package us.pochaev.jsonapi.reflection.ancestry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Stack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AncestryUtilsTest {

	@Test @DisplayName("WHEN getAncestry TestClass THEN ancestry.size is 2")
	void whenGetAncestryTestNoneThenAnscestrySize2() {
		TestClass object = new TestClass();
		Stack<Class<?>> ancestry = AncestryUtils.getAncestry(object);
		assertEquals(2, ancestry.size());
		assertThat(ancestry, contains( new Class<?> [] {
				TestClass.class,
				Object.class}));
		assertEquals(Object.class, ancestry.pop());
	}

	@Test @DisplayName("WHEN getAncestry TestClassChild THEN ancestry.size is 3")
	void whenGetAncestryTestClassChildThenAncestrySize3() {
		TestClassChild object = new TestClassChild();
		Stack<Class<?>> ancestry = AncestryUtils.getAncestry(object);
		assertEquals(3, ancestry.size());
		assertThat(ancestry, contains( new Class<?> [] {
				TestClassChild.class,
				TestClass.class,
				Object.class}));
	}

	@Test @DisplayName("WHEN getAncestry TestClassGrand THEN ancestry.size is 4")
	void whenGetAncestryTestClassGrandThenAncestrySize4() {
		TestClassGrand object = new TestClassGrand();
		Stack<Class<?>> ancestry = AncestryUtils.getAncestry(object);
		assertEquals(4, ancestry.size());
		assertThat(ancestry, contains( new Class<?> [] {
				TestClassGrand.class,
				TestClassChild.class,
				TestClass.class,
				Object.class}));
	}

}
