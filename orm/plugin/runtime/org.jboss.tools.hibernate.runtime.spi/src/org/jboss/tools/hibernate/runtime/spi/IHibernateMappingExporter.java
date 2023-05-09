package org.jboss.tools.hibernate.runtime.spi;

import java.io.File;
import java.util.Map;

public interface IHibernateMappingExporter {

	void start();
	File getOutputDirectory();
	void setOutputDirectory(File directory);
	void exportPOJO(Map<Object, Object> map, Object pojoClass);
	void setExportPOJODelegate(IExportPOJODelegate delegate);

}
