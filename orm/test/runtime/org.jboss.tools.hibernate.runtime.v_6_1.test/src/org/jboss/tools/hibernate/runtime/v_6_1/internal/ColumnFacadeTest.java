package org.jboss.tools.hibernate.runtime.v_6_1.internal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hibernate.mapping.Column;
import org.jboss.tools.hibernate.runtime.common.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.IColumn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ColumnFacadeTest {

	private static final IFacadeFactory FACADE_FACTORY = new FacadeFactoryImpl();
	
	private IColumn columnFacade = null; 
	private Column column = null;
	
	@BeforeEach
	public void beforeEach() {
		column = new Column();
		columnFacade = new ColumnFacadeImpl(FACADE_FACTORY, column);
	}
	
	@Test
	public void testInstance() {
		assertNotNull(column);
		assertNotNull(columnFacade);
	}
	
}