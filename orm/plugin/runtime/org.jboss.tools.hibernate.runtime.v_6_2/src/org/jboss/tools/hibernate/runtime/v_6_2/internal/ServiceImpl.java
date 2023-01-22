package org.jboss.tools.hibernate.runtime.v_6_2.internal;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.api.export.Exporter;
import org.hibernate.tool.api.export.ExporterConstants;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.ide.completion.HQLCodeAssist;
import org.hibernate.tool.internal.export.cfg.CfgExporter;
import org.hibernate.tool.internal.export.common.DefaultArtifactCollector;
import org.hibernate.tool.internal.reveng.strategy.OverrideRepository;
import org.hibernate.tool.internal.reveng.strategy.TableFilter;
import org.jboss.tools.hibernate.runtime.common.AbstractService;
import org.jboss.tools.hibernate.runtime.common.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.common.Util;
import org.jboss.tools.hibernate.runtime.spi.IArtifactCollector;
import org.jboss.tools.hibernate.runtime.spi.ICfg2HbmTool;
import org.jboss.tools.hibernate.runtime.spi.IColumn;
import org.jboss.tools.hibernate.runtime.spi.IConfiguration;
import org.jboss.tools.hibernate.runtime.spi.IEnvironment;
import org.jboss.tools.hibernate.runtime.spi.IExporter;
import org.jboss.tools.hibernate.runtime.spi.IHQLCodeAssist;
import org.jboss.tools.hibernate.runtime.spi.IHQLQueryPlan;
import org.jboss.tools.hibernate.runtime.spi.IHibernateMappingExporter;
import org.jboss.tools.hibernate.runtime.spi.INamingStrategy;
import org.jboss.tools.hibernate.runtime.spi.IOverrideRepository;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IProgressListener;
import org.jboss.tools.hibernate.runtime.spi.IProperty;
import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringSettings;
import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringStrategy;
import org.jboss.tools.hibernate.runtime.spi.ISchemaExport;
import org.jboss.tools.hibernate.runtime.spi.ISessionFactory;
import org.jboss.tools.hibernate.runtime.spi.ITable;
import org.jboss.tools.hibernate.runtime.spi.ITableFilter;
import org.jboss.tools.hibernate.runtime.spi.ITypeFactory;
import org.jboss.tools.hibernate.runtime.spi.IValue;
import org.jboss.tools.hibernate.runtime.v_6_2.internal.util.ConfigurationMetadataDescriptor;
import org.jboss.tools.hibernate.runtime.v_6_2.internal.util.DummyMetadataDescriptor;
import org.jboss.tools.hibernate.runtime.v_6_2.internal.util.JdbcMetadataConfiguration;
import org.xml.sax.EntityResolver;

public class ServiceImpl extends AbstractService {

	private static final String HIBERNATE_VERSION = "6.2";

	private IFacadeFactory facadeFactory = new FacadeFactoryImpl();

	@Override
	public Map<String, List<ITable>> collectDatabaseTables(Properties arg0, IReverseEngineeringStrategy arg1,
			IProgressListener arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IExporter createExporter(String exporterClassName) {
		Exporter exporter = (Exporter)Util.getInstance(
				exporterClassName, 
				facadeFactory.getClassLoader());
		if (CfgExporter.class.isAssignableFrom(exporter.getClass())) {
			exporter.getProperties().put(
					ExporterConstants.METADATA_DESCRIPTOR, 
					new DummyMetadataDescriptor());
		} else {
			exporter.getProperties().put(
					ExporterConstants.METADATA_DESCRIPTOR,
					new ConfigurationMetadataDescriptor((ConfigurationFacadeImpl)newDefaultConfiguration()));
		}
		return facadeFactory.createExporter(exporter);
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getClassWithoutInitializingProxy(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getDriverManagerConnectionProviderClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEnvironment getEnvironment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getJPAMappingFilePaths(String arg0, EntityResolver arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReverseEngineeringStrategyClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInitialized(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IConfiguration newAnnotationConfiguration() {
		return newDefaultConfiguration();
	}

	@Override
	public IValue newArray(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IArtifactCollector newArtifactCollector() {
		return facadeFactory.createArtifactCollector(new DefaultArtifactCollector());
	}

	@Override
	public IValue newBag(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICfg2HbmTool newCfg2HbmTool() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IColumn newColumn(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConfiguration newDefaultConfiguration() {
		getUsageTracker().trackNewConfigurationEvent(HIBERNATE_VERSION);
		return facadeFactory.createConfiguration(new Configuration());
	}

	@Override
	public IReverseEngineeringStrategy newDefaultReverseEngineeringStrategy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String newDialect(Properties arg0, Connection arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IHQLCodeAssist newHQLCodeAssist(IConfiguration hcfg) {
		IHQLCodeAssist result = null;
		if (hcfg instanceof ConfigurationFacadeImpl) {
			result = facadeFactory.createHQLCodeAssist(
					new HQLCodeAssist(((ConfigurationFacadeImpl)hcfg).getMetadata()));
		}
		return result;
	}

	@Override
	public IHQLQueryPlan newHQLQueryPlan(String arg0, boolean arg1, ISessionFactory arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IHibernateMappingExporter newHibernateMappingExporter(
			IConfiguration hcfg, File file) {
		return facadeFactory.createHibernateMappingExporter(
				new HibernateMappingExporterExtension(
						facadeFactory,
						hcfg,
						file));
	}

	@Override
	public IConfiguration newJDBCMetaDataConfiguration() {
		return facadeFactory.createConfiguration(new JdbcMetadataConfiguration());
	}

	@Override
	public IPersistentClass newJoinedSubclass(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IConfiguration newJpaConfiguration(String arg0, String arg1, Map<Object, Object> arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValue newList(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValue newManyToOne(ITable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValue newMap(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INamingStrategy newNamingStrategy(String strategyClassName) {
		try {
			return facadeFactory.createNamingStrategy(
					Class.forName(strategyClassName).getDeclaredConstructor().newInstance());
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	@Override
	public IValue newOneToMany(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValue newOneToOne(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IOverrideRepository newOverrideRepository() {
		return facadeFactory.createOverrideRepository(new OverrideRepository());
	}

	@Override
	public IValue newPrimitiveArray(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProperty newProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReverseEngineeringSettings newReverseEngineeringSettings(IReverseEngineeringStrategy arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReverseEngineeringStrategy newReverseEngineeringStrategy(String arg0, IReverseEngineeringStrategy arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistentClass newRootClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISchemaExport newSchemaExport(IConfiguration hcfg) {
		SchemaExportFacadeImpl result = 
			(SchemaExportFacadeImpl)facadeFactory.createSchemaExport(new SchemaExport());
		result.setConfiguration(hcfg);
		return result;
	}

	@Override
	public IValue newSet(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValue newSimpleValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistentClass newSingleTableSubclass(IPersistentClass arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistentClass newSpecialRootClass(IProperty arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITable newTable(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITableFilter newTableFilter() {
		return facadeFactory.createTableFilter(new TableFilter());
	}

	@Override
	public ITypeFactory newTypeFactory() {
		return facadeFactory.createTypeFactory();
	}

}