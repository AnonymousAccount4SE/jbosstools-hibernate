package org.jboss.tools.hibernate.runtime.v_4_0.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.SimpleValue;
import org.hibernate.mapping.Table;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.jboss.tools.hibernate.runtime.common.AbstractClassMetadataFacade;
import org.jboss.tools.hibernate.runtime.common.IFacade;
import org.jboss.tools.hibernate.runtime.spi.IClassMetadata;
import org.jboss.tools.hibernate.runtime.spi.ISession;
import org.jboss.tools.hibernate.runtime.spi.IType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClassMetadataFacadeTest {

	private static final FacadeFactoryImpl FACADE_FACTORY = new FacadeFactoryImpl();
	
	private ClassMetadata classMetadataTarget;
	private IClassMetadata classMetadataFacade;
	
	@BeforeEach
	public void beforeEach() throws Exception {
		classMetadataTarget = setupFooBarPersister();
		classMetadataFacade = new AbstractClassMetadataFacade(FACADE_FACTORY, classMetadataTarget) {};
	}
	
	@Test
	public void testGetEntityName() {
		assertEquals("foobar", classMetadataFacade.getEntityName());
	}
	
	@Test
	public void testGetIdentifierPropertyName() {
		assertEquals("foo", classMetadataFacade.getIdentifierPropertyName());
	}
	
	@Test
	public void testGetPropertyNames() {
		assertSame(PROPERTY_NAMES, classMetadataFacade.getPropertyNames());
	}
	
	@Test
	public void testGetPropertyTypes() {
		IType[] typeFacades = classMetadataFacade.getPropertyTypes();
		assertSame(TYPE_INSTANCE, ((IFacade)typeFacades[0]).getTarget());
 	}
	
	@Test
	public void testGetMappedClass() {
		assertSame(FooBar.class, classMetadataFacade.getMappedClass());
	}
	
	@Test
	public void testGetIdentifierType() {
		assertSame(TYPE_INSTANCE, ((IFacade)classMetadataFacade.getIdentifierType()).getTarget());
	}
	
	@Test
	public void testGetPropertyValue() {
		assertSame(PROPERTY_VALUE, classMetadataFacade.getPropertyValue(null, null));
	}
	
	@Test
	public void testHasIdentifierProperty() {
		assertFalse(classMetadataFacade.hasIdentifierProperty());
		((TestEntityPersister)classMetadataTarget).hasIdentifierProperty = true;
		assertTrue(classMetadataFacade.hasIdentifierProperty());
	}
	
	@Test 
	public void testGetIdentifier() {
		assertNull(((TestEntityPersister)classMetadataTarget).session);
		final SessionImplementor sessionTarget = createSession();
		ISession sessionFacade = FACADE_FACTORY.createSession(sessionTarget);
		@SuppressWarnings("serial")
		Serializable theObject = new Serializable() {};
		Object anotherObject = classMetadataFacade.getIdentifier(theObject, sessionFacade);
		assertSame(theObject, anotherObject);
		assertSame(sessionTarget, ((TestEntityPersister)classMetadataTarget).session);
	}
	
	@Test
	public void testIsInstanceOfAbstractEntityPersister() {
		assertTrue(classMetadataFacade.isInstanceOfAbstractEntityPersister());
		classMetadataTarget = (ClassMetadata)Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[] { ClassMetadata.class }, 
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				});	
		classMetadataFacade = new AbstractClassMetadataFacade(FACADE_FACTORY, classMetadataTarget) {};
		assertFalse(classMetadataFacade.isInstanceOfAbstractEntityPersister());
	}
	
	@Test
	public void testGetTuplizerPropertyValue() {
		assertEquals(0, classMetadataFacade.getTuplizerPropertyValue(new FooBar(), 0));
	}
	
	@Test
	public void testGetPropertyIndexOrNull() {
		assertSame(0, classMetadataFacade.getPropertyIndexOrNull("bar"));
	}
	
	private ClassMetadata setupFooBarPersister() {
		Configuration configuration = new Configuration();
		configuration.setProperty(AvailableSettings.DIALECT, TestDialect.class.getName());
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
		builder.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = builder.buildServiceRegistry();		
		SessionFactoryImplementor sfi = (SessionFactoryImplementor)configuration.buildSessionFactory(serviceRegistry);
		return new TestEntityPersister(createPersistentClass(configuration), sfi);
	}
	
	private PersistentClass createPersistentClass(Configuration configuration) {
		RootClass rc = new RootClass();
		Table t = new Table("foobar");
		rc.setTable(t);
		Column c = new Column("foo");
		t.addColumn(c);
		ArrayList<Column> keyList = new ArrayList<>();
		keyList.add(c);
		t.createUniqueKey(keyList);
		SimpleValue sv = new SimpleValue(configuration.createMappings());
		sv.setNullValue("null");
		sv.setTypeName(Integer.class.getName());
		sv.addColumn(c);
		sv.setTable(t);
		rc.setEntityName("foobar");
		rc.setIdentifier(sv);
		rc.setClassName(FooBar.class.getName());
		Property p = new Property();
		p.setName("bar");
		p.setValue(sv);
		rc.addProperty(p);
		return rc;
	}
	
	private static final Object PROPERTY_VALUE = new Object();
	private static final String[] PROPERTY_NAMES = new String[] {};
	private static final Type TYPE_INSTANCE = new StringType();
	
	private static class TestEntityPersister extends SingleTableEntityPersister {
		
		private boolean hasIdentifierProperty = false;
		private SessionImplementor session = null;
		
		public TestEntityPersister(
				PersistentClass pc, 
				SessionFactoryImplementor sfi) throws HibernateException {
			super(pc, null, sfi, null);
		}
		
		@Override
		public Object getPropertyValue(Object object, String propertyName) {
			return PROPERTY_VALUE;
		}
		
		@Override
		public String getIdentifierPropertyName() {
			return "foo";
		}
		
		@Override
		public String[] getPropertyNames() {
			return PROPERTY_NAMES;
		}
		
		@Override
		public Type[] getPropertyTypes() {
			return new Type[] { TYPE_INSTANCE };
		}
		
		@Override
		public Type getIdentifierType() {
			return TYPE_INSTANCE;
 		}
		
		@Override
		public boolean hasIdentifierProperty() {
			return hasIdentifierProperty;
		}
		
		@Override
		public Serializable getIdentifier(Object object, SessionImplementor s) {
			session = s;
			return (Serializable)object;
		}
		
	}
	
	public static SessionImplementor createSession() {
		return (SessionImplementor)Proxy.newProxyInstance(
				ClassMetadataFacadeTest.class.getClassLoader(), 
				new Class[] { SessionImplementor.class },
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
		});
	}
	
	public class FooBar {
		public int id = 1967;
		public int getBar() {
			return 0;
		}
		public void setBar(int b) {}
	}
	
	public static class TestDialect extends Dialect {}
	
}
