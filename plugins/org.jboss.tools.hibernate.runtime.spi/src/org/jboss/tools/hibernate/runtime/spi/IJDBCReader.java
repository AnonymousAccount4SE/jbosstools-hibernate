package org.jboss.tools.hibernate.runtime.spi;


public interface IJDBCReader {

	void readDatabaseSchema(
			IDatabaseCollector databaseCollector,
			String defaultCatalogName, 
			String defaultSchemaName,
			IProgressListener progressListener);

}
