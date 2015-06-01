package org.jboss.tools.hibernate.runtime.common;

import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringSettings;
import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringStrategy;

public abstract class AbstractReverseEngineeringStrategyFacade 
extends AbstractFacade 
implements IReverseEngineeringStrategy {

	public AbstractReverseEngineeringStrategyFacade(
			IFacadeFactory facadeFactory, 
			Object target) {
		super(facadeFactory, target);
	}

	@Override
	public void setSettings(IReverseEngineeringSettings settings) {
		assert settings instanceof IFacade;
		Util.invokeMethod(
				getTarget(), 
				"setSettings", 
				new Class[] { getReverseEngineeringSettingsClass() }, 
				new Object[] { ((IFacade)settings).getTarget() });
	}
	
	protected Class<?> getReverseEngineeringSettingsClass() {
		return Util.getClass(
				getReverseEngineeringSettingsClassName(), 
				getFacadeFactoryClassLoader());
	}
	
	protected String getReverseEngineeringSettingsClassName() {
		return "org.hibernate.cfg.reveng.ReverseEngineeringSettings";
	}

}
