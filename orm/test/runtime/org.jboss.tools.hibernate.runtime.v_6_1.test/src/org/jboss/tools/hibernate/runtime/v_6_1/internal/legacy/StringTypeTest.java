package org.jboss.tools.hibernate.runtime.v_6_1.internal.legacy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringTypeTest {
	
	@Test
	public void testInstance() {
		assertNotNull(StringType.INSTANCE);
	}
	
	@Test
	public void testGetName() {
		assertEquals("string", StringType.INSTANCE.getName());
	}
	
	@Test
	public void testRegisterUnderJavaType() {
		assertTrue(StringType.INSTANCE.registerUnderJavaType());
	}

}
