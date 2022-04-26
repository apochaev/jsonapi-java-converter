package us.pochaev.jsonapi.v1_0.converter.to.membername;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import us.pochaev.jsonapi.v1_0.converter.to.exceptions.JsonApiSpecificationViolation;

class MemberNameFactoryTest {

	@Test @DisplayName("WHEN valid member name THEN name is returned")
	void test1() {
		String name = "abc";
		String memberName = MemberNameFactory.create(name);
		assertEquals(name, memberName);
	}

	@Test @DisplayName("WHEN invalid member name THEN exception")
	void test2() {
		String name = " abc ";
		assertThrows(JsonApiSpecificationViolation.class, () -> MemberNameFactory.create(name));
	}
}
