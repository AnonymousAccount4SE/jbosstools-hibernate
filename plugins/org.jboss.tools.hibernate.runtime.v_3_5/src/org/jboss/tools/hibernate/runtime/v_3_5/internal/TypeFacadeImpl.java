package org.jboss.tools.hibernate.runtime.v_3_5.internal;

import org.hibernate.type.Type;
import org.jboss.tools.hibernate.runtime.common.AbstractTypeFacade;
import org.jboss.tools.hibernate.runtime.common.IFacadeFactory;

public class TypeFacadeImpl extends AbstractTypeFacade {
	
	public TypeFacadeImpl(
			IFacadeFactory facadeFactory,
			Type type) {
		super(facadeFactory, type);
	}	

	protected String getStringRepresentableTypeClassName() {
		return "org.hibernate.type.NullableType";
	}

}
