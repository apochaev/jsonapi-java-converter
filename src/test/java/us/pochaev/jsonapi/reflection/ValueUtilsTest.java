package us.pochaev.jsonapi.reflection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValueUtilsTest {


	@Test @DisplayName("WHEN capitalize camelCase THEN CamelCase")
	void testCapitalizeCamelCase() {
		String actual = ValueUtils.capitalize("camelCase");
		assertEquals("CamelCase", actual);
	}



//	@Test @DisplayName("WHEN object class instead of object THEN empty collection")
//	void whenObjectClassThenEmptyCollection() {
//		Collection<ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(TestMemberAnnotation.class, TestNone.class);
//		assertTrue(valueDescriptors.isEmpty());
//	}
//
//	@Test @DisplayName("WHEN none THEN empty collection")
//	void whenNoneThenEmptyCollection() {
//		TestNone object = new TestNone();
//		Collection<ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(TestMemberAnnotation.class, object);
//		assertTrue(valueDescriptors.isEmpty());
//	}
//
//	@Test @DisplayName("WHEN most THEN x results")
//	void whenMostThenCollection() {
//		TestMost object = new TestMost();
//		Collection<ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(TestMemberAnnotation.class, object);
//		assertEquals(-1, valueDescriptors.size());
//	}
}
