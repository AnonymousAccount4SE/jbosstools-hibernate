package org.jboss.tools.hibernate.runtime.v_6_0.internal.util;

import java.util.Properties;

import org.hibernate.boot.Metadata;
import org.hibernate.tool.api.metadata.MetadataConstants;
import org.hibernate.tool.api.reveng.RevengStrategy;

public class JdbcMetadataConfiguration {
	
	Properties properties = new Properties();
	RevengStrategy revengStrategy;
	Metadata metadata;

	public Properties getProperties() {
		return properties;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public Object getProperty(String key) {
		return this.properties.get(key);
	}
	
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}

	public void addProperties(Properties properties) {
		this.properties.putAll(properties);
	}

	public Object getReverseEngineeringStrategy() {
		return revengStrategy;
	}

	public void setReverseEngineeringStrategy(RevengStrategy strategy) {
		this.revengStrategy = strategy;
	}

	public boolean preferBasicCompositeIds() {
		Object preferBasicCompositeIds = properties.get(MetadataConstants.PREFER_BASIC_COMPOSITE_IDS);
		return preferBasicCompositeIds == null ? false : ((Boolean)preferBasicCompositeIds).booleanValue();
	}

	public void setPreferBasicCompositeIds(boolean preferBasicCompositeIds) {
		properties.put(
				MetadataConstants.PREFER_BASIC_COMPOSITE_IDS, 
				Boolean.valueOf(preferBasicCompositeIds));
	}

	public Metadata getMetadata() {
		return metadata;
	}
	
}
