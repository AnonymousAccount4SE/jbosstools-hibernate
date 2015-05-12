package org.jboss.tools.hibernate.runtime.common;

import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IProperty;

public abstract class AbstractPersistentClassFacade 
extends AbstractFacade 
implements IPersistentClass {

	protected IProperty identifierProperty = null;

	public AbstractPersistentClassFacade(
			IFacadeFactory facadeFactory, 
			Object target) {
		super(facadeFactory, target);
	}

	@Override
	public String getClassName() {
		return (String)Util.invokeMethod(
				getTarget(), 
				"getClassName", 
				new Class[] {}, 
				new Object[] {});
	}

	@Override
	public String getEntityName() {
		return (String)Util.invokeMethod(
				getTarget(), 
				"getEntityName", 
				new Class[] {}, 
				new Object[] {});
	}

	@Override
	public boolean isAssignableToRootClass() {
		return getRootClassClass().isAssignableFrom(getTarget().getClass());
	}
	
	@Override
	public boolean isRootClass() {
		return getTarget().getClass() == getRootClassClass();
	}

	protected Class<?> getRootClassClass() {
		return Util.getClass(getRootClassClassName(), getFacadeFactoryClassLoader());
	}
	
	protected String getRootClassClassName() {
		return "org.hibernate.mapping.RootClass";
	}

}
