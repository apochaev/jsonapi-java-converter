package us.pochaev.jsonapi.reflection.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.reflection.annotation.JsonApiTestAnnotation;
import us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter.TestClass;
import us.pochaev.jsonapi.reflection.value.privatefieldpublicgetter.TestClassChild;

/**
 * Only testing the "happy path" as there are to many permutations of field / getter visibility combinations.
 * @author apochaev
 *
 */
class ValueUtilsTest {

	@Test @DisplayName("WHEN object class instead of object THEN empty collection")
	void whenObjectClassThenEmptyCollection() {
		Map<String, ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(JsonApiTestAnnotation.class, TestClass.class);
		assertTrue(valueDescriptors.isEmpty());
	}

	@Test @DisplayName("WHEN parent THEN parent value descriptors")
	void test2() {
		TestClass object = new TestClass();
		Map<String, ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(JsonApiTestAnnotation.class, object);

		assertEquals(2, valueDescriptors.size());
		assertEquals("privateTestClassField", valueDescriptors.get("privateTestClassField").getValue(object));
		assertEquals("testClassGetter", valueDescriptors.get("testClassGetter").getValue(object));
	}

	@Test @DisplayName("WHEN child THEN child and parent value descriptors")
	void test3() {
		TestClassChild object = new TestClassChild();
		Map<String, ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(JsonApiTestAnnotation.class, object);

		assertEquals(3, valueDescriptors.size());
		assertEquals("testClassGetter", valueDescriptors.get("testClassGetter").getValue(object));
		assertEquals("privateTestClassChildField", valueDescriptors.get("privateTestClassField").getValue(object)); //override
		assertEquals("privateTestClassChildField", valueDescriptors.get("privateTestClassChildField").getValue(object));
	}

	@Test @DisplayName("WHEN grand child THEN grand, child and parent value descriptors")
	void test4() {
		TestClassChild object = new TestClassChild();
		Map<String, ValueDescriptor> valueDescriptors = ValueUtils.getValueDescriptors(JsonApiTestAnnotation.class, object);

		assertEquals(3, valueDescriptors.size());
		assertEquals("testClassGetter", valueDescriptors.get("testClassGetter").getValue(object));
		assertEquals("privateTestClassChildField", valueDescriptors.get("privateTestClassField").getValue(object)); //override
		assertEquals("privateTestClassChildField", valueDescriptors.get("privateTestClassChildField").getValue(object));
		// grand child has method with the annotation that does not conform to getter naming convention
	}


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
