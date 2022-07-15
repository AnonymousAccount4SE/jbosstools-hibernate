package org.jboss.tools.hibernate.runtime.v_6_1.internal.legacy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ClassTypeTest {
	
	@Test
	public void testInstance() {
		assertNotNull(ClassType.INSTANCE);
	}
	
	@Test
	public void testGetName() {
		assertEquals("class", ClassType.INSTANCE.getName());
	}
	
	@Test
	public void testRegisterUnderJavaType() {
		assertTrue(ClassType.INSTANCE.registerUnderJavaType());
	}

}
