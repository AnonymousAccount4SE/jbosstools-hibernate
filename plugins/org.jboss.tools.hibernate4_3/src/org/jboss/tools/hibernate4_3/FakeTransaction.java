package org.jboss.tools.hibernate4_3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

public class FakeTransaction implements Transaction {
	
	int status;
	private Connection connection;
	List<Synchronization> synchronizations = new ArrayList<Synchronization>();
	private FakeTransactionManager transactionManager;
	
	FakeTransaction(FakeTransactionManager transactionManager) {
		status = Status.STATUS_NO_TRANSACTION;
		this.transactionManager = transactionManager;
	}
	
	public void begin() throws SystemException {
		try {
			connection = transactionManager.connections.getConnection();
		}
		catch (SQLException sqle) {
			
			sqle.printStackTrace();
			throw new SystemException(sqle.toString());
		}
		status = Status.STATUS_ACTIVE;
	}

	public void commit() throws RollbackException, HeuristicMixedException,
			HeuristicRollbackException, SecurityException,
			IllegalStateException, SystemException {
		
		if (status == Status.STATUS_MARKED_ROLLBACK) {
			rollback();
		}
		else {
			status = Status.STATUS_PREPARING;
			
			for (Synchronization s : synchronizations) {
				s.beforeCompletion();
			}
			
			status = Status.STATUS_COMMITTING;
			
			try {
				connection.commit();
				connection.close();
			}
			catch (SQLException sqle) {
				status = Status.STATUS_UNKNOWN;
				throw new SystemException();
			}
			
			status = Status.STATUS_COMMITTED;

			for (Synchronization s : synchronizations) {
				s.afterCompletion(status);
			}
			
			//status = Status.STATUS_NO_TRANSACTION;
			transactionManager.endCurrent(this);
		}

	}
	
	public boolean delistResource(XAResource arg0, int arg1)
			throws IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean enlistResource(XAResource arg0) throws RollbackException,
			IllegalStateException, SystemException {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getStatus() throws SystemException {
		return status;
	}
	
	public void registerSynchronization(Synchronization sync)
			throws RollbackException, IllegalStateException, SystemException {
		synchronizations.add(sync);
	}
	
	public void rollback() throws IllegalStateException, SystemException {

		status = Status.STATUS_ROLLING_BACK;

// Synch.beforeCompletion() should *not* be called for rollbacks
//		for ( int i=0; i<synchronizations.size(); i++ ) {
//			Synchronization s = (Synchronization) synchronizations.get(i);
//			s.beforeCompletion();
//		}
		
		status = Status.STATUS_ROLLEDBACK;
		
		try {
			connection.rollback();
			connection.close();
		}
		catch (SQLException sqle) {
			status = Status.STATUS_UNKNOWN;
			throw new SystemException();
		}
		
		for (Synchronization s : synchronizations) {
			s.afterCompletion(status);
		}
		
		//status = Status.STATUS_NO_TRANSACTION;
		transactionManager.endCurrent(this);
	}
	
	public void setRollbackOnly() throws IllegalStateException, SystemException {
		status = Status.STATUS_MARKED_ROLLBACK;
	}

	void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}
	
}

