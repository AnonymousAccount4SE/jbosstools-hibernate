package org.jboss.tools.hibernate.runtime.common;

import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringSettings;

public abstract class AbstractReverseEngineeringSettingsFacade 
extends AbstractFacade 
implements IReverseEngineeringSettings {

	public AbstractReverseEngineeringSettingsFacade(IFacadeFactory facadeFactory, Object target) {
		super(facadeFactory, target);
	}

	@Override
	public IReverseEngineeringSettings setDefaultPackageName(String name) {
		Util.invokeMethod(
				getTarget(), 
				"setDefaultPackageName", 
				new Class[] { String.class }, 
				new Object[] { name } );
		return this;
	}

	@Override
	public IReverseEngineeringSettings setDetectManyToMany(boolean b) {
		Util.invokeMethod(
				getTarget(), 
				"setDetectManyToMany", 
				new Class[] { boolean.class }, 
				new Object[] { b } );
		return this;
	}

	@Override
	public IReverseEngineeringSettings setDetectOneToOne(boolean b) {
		Util.invokeMethod(
				getTarget(), 
				"setDetectOneToOne", 
				new Class[] { boolean.class }, 
				new Object[] { b } );
		return this;
	}

	@Override
	public IReverseEngineeringSettings setDetectOptimisticLock(boolean b) {
		Util.invokeMethod(
				getTarget(), 
				"setDetectOptimisticLock", 
				new Class[] { boolean.class }, 
				new Object[] { b } );
		return this;
	}

}
