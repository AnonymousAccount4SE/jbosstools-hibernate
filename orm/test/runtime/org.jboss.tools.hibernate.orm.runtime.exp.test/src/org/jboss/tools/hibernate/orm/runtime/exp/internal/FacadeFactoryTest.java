package org.jboss.tools.hibernate.orm.runtime.exp.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.tool.internal.reveng.strategy.OverrideRepository;
import org.junit.jupiter.api.Test;

public class FacadeFactoryTest {

	private static FacadeFactoryImpl FACADE_FACTORY = new FacadeFactoryImpl();

	@Test
	public void testFacadeFactoryCreation() {
		assertNotNull(FACADE_FACTORY);
	}
	
	@Test
	public void testGetClassLoader() {
		assertSame(
				FacadeFactoryImpl.class.getClassLoader(), 
				FACADE_FACTORY.getClassLoader());
	}
	
	@Test
	public void testCreateArtifactCollector() {
		try {
			FACADE_FACTORY.createArtifactCollector(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateCfg2HbmTool() {
		try {
			FACADE_FACTORY.createCfg2HbmTool(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateNamingStrategy() {
		try {
			FACADE_FACTORY.createNamingStrategy((String)null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateReverseEngineeringStrategy() {
		try {
			FACADE_FACTORY.createReverseEngineeringStrategy(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateReverseEngineeringSettings() {
		try {
			FACADE_FACTORY.createReverseEngineeringStrategy(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateOverrideRepository() {
		try {
			FACADE_FACTORY.createOverrideRepository(new OverrideRepository());
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateConfiguration() {
		try {
			FACADE_FACTORY.createConfiguration(new Configuration());
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateColumn() {
		try {
			FACADE_FACTORY.createColumn(new Column());
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateSessionFactory() {
		try {
			FACADE_FACTORY.createSessionFactory(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateClassMetadata() {
		try {
			FACADE_FACTORY.createClassMetadata(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateCollectionMetadata() {
		try {
			FACADE_FACTORY.createCollectionMetadata(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());			
		}
	}
	
	@Test
	public void testCreateSession() {
		try {
			FACADE_FACTORY.createSession(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateCriteria() {
		try {
			FACADE_FACTORY.createCriteria(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateSpecialRootClass() {
		try {
			FACADE_FACTORY.createSpecialRootClass(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreatePersistentClass() {
		try {
			FACADE_FACTORY.createPersistentClass(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateJoin() {
		try {
			FACADE_FACTORY.createJoin(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateHQLCompletionProposal() {
		try {
			FACADE_FACTORY.createHQLCompletionProposal(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}	
	
	@Test
	public void testCreateProperty() {
		try {
			FACADE_FACTORY.createProperty(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateTable() {
		try {
			FACADE_FACTORY.createTable(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateValue() {
		try {
			FACADE_FACTORY.createValue(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateTableFilter() {
		try {
			FACADE_FACTORY.createTableFilter(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateTypeFactory() {
		try {
			FACADE_FACTORY.createTypeFactory();
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateType() {
		try {
			FACADE_FACTORY.createType(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateForeignKey() {
		try {
			FACADE_FACTORY.createForeignKey(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreatePrimaryKey() {
		try {
			FACADE_FACTORY.createPrimaryKey(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateEnvironment() {
		try {
			FACADE_FACTORY.createEnvironment();
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateSchemaExport() {
		try {
			FACADE_FACTORY.createSchemaExport(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateHibernateMappingExporter() {
		try {
			FACADE_FACTORY.createHibernateMappingExporter(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateExporter() {
		try {
			FACADE_FACTORY.createExporter(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateGenericExporter() {
		try {
			FACADE_FACTORY.createGenericExporter(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateHbm2DDLExporter() {
		try {
			FACADE_FACTORY.createHbm2DDLExporter(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateQueryExporter() {
		try {
			FACADE_FACTORY.createQueryExporter(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateHQLCodeAssist() {
		try {
			FACADE_FACTORY.createHQLCodeAssist(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
	@Test
	public void testCreateQuery() {
		try {
			FACADE_FACTORY.createQuery(null);
			fail();
		} catch (Throwable t) {
			assertEquals("Should use class 'NewFacadeFactory'", t.getMessage());
		}
	}
	
}
