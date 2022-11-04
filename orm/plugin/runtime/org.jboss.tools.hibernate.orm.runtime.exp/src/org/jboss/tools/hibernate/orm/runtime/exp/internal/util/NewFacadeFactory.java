package org.jboss.tools.hibernate.orm.runtime.exp.internal.util;

import org.hibernate.tool.orm.jbt.wrp.WrapperFactory;
import org.jboss.tools.hibernate.runtime.common.AbstractFacadeFactory;
import org.jboss.tools.hibernate.runtime.common.AbstractNamingStrategyFacade;
import org.jboss.tools.hibernate.runtime.spi.IArtifactCollector;
import org.jboss.tools.hibernate.runtime.spi.ICfg2HbmTool;
import org.jboss.tools.hibernate.runtime.spi.INamingStrategy;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IProperty;

public class NewFacadeFactory extends AbstractFacadeFactory {

	private WrapperFactory wrapperFactory = new WrapperFactory();
	
	@Override
	public IArtifactCollector createArtifactCollector(Object target) {
		throw new RuntimeException("Use 'NewFacadeFactory#createArtifactCollector()");
	}
	
	public IArtifactCollector createArtifactCollector() {
		return (IArtifactCollector)GenericFacadeFactory.createFacade(
				IArtifactCollector.class, 
				wrapperFactory.createArtifactCollectorWrapper());
	}

	@Override
	public ICfg2HbmTool createCfg2HbmTool(Object target) {
		throw new RuntimeException("Use 'NewFacadeFactory#createCfg2HbmTool()");
	}
	
	public ICfg2HbmTool createCfg2HbmTool() {
		return (ICfg2HbmTool)GenericFacadeFactory.createFacade(
				ICfg2HbmTool.class,
				wrapperFactory.createCfg2HbmWrapper());
	}
	
	@Override
	public INamingStrategy createNamingStrategy(Object target) {
		throw new RuntimeException("Use 'NewFacadeFactory#createNamingStrategy(String)");
	}
	
	public INamingStrategy createNamingStrategy(String namingStrategyClassName) {
		return (INamingStrategy)GenericFacadeFactory.createFacade(
				INamingStrategy.class, 
				wrapperFactory.createNamingStrategyWrapper(namingStrategyClassName));
	}

	
	
	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistentClass createSpecialRootClass(IProperty property) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
