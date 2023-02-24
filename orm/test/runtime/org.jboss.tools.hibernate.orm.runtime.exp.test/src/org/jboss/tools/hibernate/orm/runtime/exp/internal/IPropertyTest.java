package org.jboss.tools.hibernate.orm.runtime.exp.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.mapping.BasicValue;
import org.hibernate.mapping.Component;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Table;
import org.hibernate.mapping.Value;
import org.hibernate.tool.orm.jbt.util.DummyMetadataBuildingContext;
import org.hibernate.tool.orm.jbt.wrp.PersistentClassWrapper;
import org.jboss.tools.hibernate.orm.runtime.exp.internal.util.NewFacadeFactory;
import org.jboss.tools.hibernate.runtime.common.IFacade;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IProperty;
import org.jboss.tools.hibernate.runtime.spi.IType;
import org.jboss.tools.hibernate.runtime.spi.IValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IPropertyTest {
	
	private IProperty propertyFacade = null;
	private Property propertyTarget = null;
	
	@BeforeEach
	public void beforeEach() {
		propertyFacade = NewFacadeFactory.INSTANCE.createProperty();
		propertyTarget = (Property)((IFacade)propertyFacade).getTarget();
	}
	
	@Test
	public void testConstruction() {
		assertNotNull(propertyFacade);
		assertNotNull(propertyTarget);
	}

	@Test
	public void testGetValue() {
		assertNull(propertyFacade.getValue());
		Value valueTarget = new BasicValue(DummyMetadataBuildingContext.INSTANCE);
		propertyTarget.setValue(valueTarget);
		IValue valueFacade = propertyFacade.getValue();
		assertNotNull(valueFacade);
		assertSame(valueTarget, ((IFacade)valueFacade).getTarget());
	}
	
	@Test
	public void testSetName() {
		assertNotEquals("foo", propertyTarget.getName());
		propertyFacade.setName("foo");
		assertEquals("foo", propertyTarget.getName());
	}
	
	@Test
	public void testSetPersistentClass() {
		assertNull(propertyTarget.getPersistentClass());
		IPersistentClass persistentClassFacade = NewFacadeFactory.INSTANCE.createRootClass();
		PersistentClassWrapper persistentClassTarget = 
				(PersistentClassWrapper)((IFacade)persistentClassFacade).getTarget();
		propertyFacade.setPersistentClass(persistentClassFacade);
		assertSame(persistentClassTarget.getWrappedObject(), propertyTarget.getPersistentClass());
	}
	
	@Test
	public void testIsComposite() {
		propertyTarget.setValue(new BasicValue(DummyMetadataBuildingContext.INSTANCE));
		assertFalse(propertyFacade.isComposite());
		Component component = new Component(
				DummyMetadataBuildingContext.INSTANCE, 
				new Table(""), 
				new RootClass(DummyMetadataBuildingContext.INSTANCE));
		propertyTarget.setValue(component);
		assertTrue(propertyFacade.isComposite());
	}
	
	@Test
	public void testGetPropetyAccessorName() {
		assertNotEquals("foo", propertyFacade.getPropertyAccessorName());
		propertyTarget.setPropertyAccessorName("foo");
		assertEquals("foo", propertyFacade.getPropertyAccessorName());
	}
	
	@Test
	public void testGetName() {
		assertNotEquals("foo", propertyFacade.getName());
		propertyTarget.setName("foo");
		assertEquals("foo", propertyFacade.getName());
	}
	
	@Test
	public void testGetType() {
		BasicValue v = new BasicValue(DummyMetadataBuildingContext.INSTANCE);
		v.setTypeName("int");
		propertyTarget.setValue(v);
		IType typeFacade = propertyFacade.getType();
		assertEquals("integer", typeFacade.getName());
	}
	
	@Test
	public void testSetValue() {
		assertNull(propertyTarget.getValue());	
		BasicValue valueTarget = new BasicValue(DummyMetadataBuildingContext.INSTANCE);
		IValue valueFacade = NewFacadeFactory.INSTANCE.createValue(valueTarget);
		propertyFacade.setValue(valueFacade);
		assertSame(valueTarget, propertyTarget.getValue());
	}
	
	@Test
	public void testSetPropertyAccessorName() {
		assertNotEquals("foo", propertyTarget.getPropertyAccessorName());
		propertyFacade.setPropertyAccessorName("foo");
		assertEquals("foo", propertyTarget.getPropertyAccessorName());
	}
	
	@Test
	public void testSetCascade() {
		assertNotEquals("foo", propertyTarget.getCascade());
		propertyFacade.setCascade("foo");
		assertEquals("foo", propertyTarget.getCascade());
	}
	
}