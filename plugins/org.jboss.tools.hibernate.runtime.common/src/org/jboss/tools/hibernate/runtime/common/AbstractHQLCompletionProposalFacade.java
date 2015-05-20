package org.jboss.tools.hibernate.runtime.common;

import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.IHQLCompletionProposal;

public abstract class AbstractHQLCompletionProposalFacade 
extends AbstractFacade 
implements IHQLCompletionProposal {

	public AbstractHQLCompletionProposalFacade(
			IFacadeFactory facadeFactory, 
			Object target) {
		super(facadeFactory, target);
	}

	@Override
	public String getCompletion() {
		return (String)Util.invokeMethod(
				getTarget(), 
				"getCompletion", 
				new Class[] {}, 
				new Object[] {});
	}

}
