package org.jboss.tools.hibernate.runtime.v_6_1.internal.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

import org.hibernate.boot.Metadata;
import org.hibernate.tool.api.metadata.MetadataConstants;
import org.hibernate.tool.api.reveng.RevengStrategy;
import org.hibernate.tool.internal.reveng.strategy.DefaultStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JdbcMetadataConfigurationTest {

	private JdbcMetadataConfiguration jdbcMetadataConfiguration = null;
	
	@BeforeEach
	public void beforeEach() {
		jdbcMetadataConfiguration = new JdbcMetadataConfiguration();
	}
	
	@Test
	public void testInstance() {
		assertNotNull(jdbcMetadataConfiguration);
	}
	
	@Test
	public void testGetProperties() {
		Properties properties = new Properties();
		assertNotNull(jdbcMetadataConfiguration.properties);
		assertNotSame(properties,  jdbcMetadataConfiguration.getProperties());
		jdbcMetadataConfiguration.properties = properties;
		assertSame(properties, jdbcMetadataConfiguration.getProperties());
	}
	
	@Test
	public void testSetProperties() {
		Properties properties = new Properties();
		assertNotNull(jdbcMetadataConfiguration.properties);
		assertNotSame(properties,  jdbcMetadataConfiguration.getProperties());
		jdbcMetadataConfiguration.setProperties(properties);
		assertSame(properties, jdbcMetadataConfiguration.getProperties());
	}
	
	@Test
	public void testGetProperty() {
		assertNull(jdbcMetadataConfiguration.getProperty("foo"));
		jdbcMetadataConfiguration.properties.put("foo", "bar");
		assertEquals("bar", jdbcMetadataConfiguration.getProperty("foo"));
	}

	@Test
	public void testSetProperty() {
		assertNull(jdbcMetadataConfiguration.properties.get("foo"));
		jdbcMetadataConfiguration.setProperty("foo", "bar");
		assertEquals("bar", jdbcMetadataConfiguration.properties.get("foo"));
	}
	
	@Test
	public void testAddProperties() {
		Properties properties = new Properties();
		properties.put("foo", "bar");
		assertNull(jdbcMetadataConfiguration.properties.get("foo"));
		jdbcMetadataConfiguration.addProperties(properties);
		assertEquals("bar", jdbcMetadataConfiguration.properties.get("foo"));
	}
	
	@Test
	public void testGetReverseEngineeringStrategy() {
		RevengStrategy strategy = new DefaultStrategy();
		assertNull(jdbcMetadataConfiguration.getReverseEngineeringStrategy());
		jdbcMetadataConfiguration.revengStrategy = strategy;
		assertSame(strategy, jdbcMetadataConfiguration.getReverseEngineeringStrategy());
	}
	
	@Test
	public void testSetReverseEngineeringStrategy() {
		RevengStrategy strategy = new DefaultStrategy();
		assertNull(jdbcMetadataConfiguration.revengStrategy);
		jdbcMetadataConfiguration.setReverseEngineeringStrategy(strategy);
		assertSame(strategy, jdbcMetadataConfiguration.revengStrategy);
	}
	
	@Test
	public void testPreferBasicCompositeIds() {
		assertTrue(jdbcMetadataConfiguration.preferBasicCompositeIds());
		jdbcMetadataConfiguration.properties.put(MetadataConstants.PREFER_BASIC_COMPOSITE_IDS, false);
		assertFalse(jdbcMetadataConfiguration.preferBasicCompositeIds());
	}
	
	@Test
	public void testSetPreferBasicCompositeIds() {
		assertNull(
				jdbcMetadataConfiguration.properties.get(
						MetadataConstants.PREFER_BASIC_COMPOSITE_IDS));
		jdbcMetadataConfiguration.setPreferBasicCompositeIds(true);
		assertEquals(
				true, 
				jdbcMetadataConfiguration.properties.get(
						MetadataConstants.PREFER_BASIC_COMPOSITE_IDS));
	}
	
	@Test
	public void testGetMetadata() {
		Metadata metadata = (Metadata)Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[] { Metadata.class }, 
				new InvocationHandler() {					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						return null;
					}
				});
		assertNull(jdbcMetadataConfiguration.getMetadata());
		jdbcMetadataConfiguration.metadata = metadata;
		assertSame(metadata, jdbcMetadataConfiguration.getMetadata());
	}
	
}
