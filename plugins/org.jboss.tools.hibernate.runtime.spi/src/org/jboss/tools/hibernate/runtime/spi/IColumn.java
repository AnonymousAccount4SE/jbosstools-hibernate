package org.jboss.tools.hibernate.runtime.spi;

public interface IColumn {

	String getName();
	Integer getSqlTypeCode();
	String getSqlType();
	int getLength();
	int getDefaultLength();
	int getPrecision();
	int getDefaultPrecision();
	int getScale();
	int getDefaultScale();
	boolean isNullable();
	IValue getValue();
	boolean isUnique();
	String getSqlType(IDialect dialect, IConfiguration configuration);
	void setSqlType(String sqlType);

}
