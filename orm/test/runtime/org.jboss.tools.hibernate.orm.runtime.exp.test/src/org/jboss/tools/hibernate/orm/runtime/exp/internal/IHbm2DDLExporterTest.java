package org.jboss.tools.hibernate.orm.runtime.exp.internal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Properties;

import org.hibernate.tool.api.export.ExporterConstants;
import org.hibernate.tool.internal.export.common.AbstractExporter;
import org.hibernate.tool.internal.export.ddl.DdlExporter;
import org.hibernate.tool.orm.jbt.wrp.DdlExporterWrapperFactory;
import org.hibernate.tool.orm.jbt.wrp.Wrapper;
import org.jboss.tools.hibernate.orm.runtime.exp.internal.util.GenericFacadeFactory;
import org.jboss.tools.hibernate.runtime.common.IFacade;
import org.jboss.tools.hibernate.runtime.spi.IHbm2DDLExporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IHbm2DDLExporterTest {
	
	private IHbm2DDLExporter ddlExporterFacade = null;
	private DdlExporter ddlExporterTarget = null;
	
	@BeforeEach
	public void beforeEach() {
		ddlExporterFacade = (IHbm2DDLExporter)GenericFacadeFactory.createFacade(
				IHbm2DDLExporter.class, 
				DdlExporterWrapperFactory.create(new DdlExporter()));
		Object ddlExporterWrapper = ((IFacade)ddlExporterFacade).getTarget();
		ddlExporterTarget = (DdlExporter)((Wrapper)ddlExporterWrapper).getWrappedObject();
	}
	
	@Test
	public void testConstruction() {
		assertNotNull(ddlExporterTarget);
		assertNotNull(ddlExporterFacade);
	}
	
	@Test
	public void testGetProperties() throws Exception {
		Field propertiesField = AbstractExporter.class.getDeclaredField("properties");
		propertiesField.setAccessible(true);
		Properties properties = new Properties();
		assertNotSame(properties, ddlExporterFacade.getProperties());
		propertiesField.set(ddlExporterTarget, properties);
		assertSame(properties, ddlExporterFacade.getProperties());
	}

	@Test
	public void testSetExport() {
		assertNull(ddlExporterTarget.getProperties().get(ExporterConstants.EXPORT_TO_DATABASE));
		ddlExporterFacade.setExport(false);
		assertFalse((Boolean)ddlExporterTarget.getProperties().get(ExporterConstants.EXPORT_TO_DATABASE));
		ddlExporterFacade.setExport(true);
		assertTrue((Boolean)ddlExporterTarget.getProperties().get(ExporterConstants.EXPORT_TO_DATABASE));
	}

}
