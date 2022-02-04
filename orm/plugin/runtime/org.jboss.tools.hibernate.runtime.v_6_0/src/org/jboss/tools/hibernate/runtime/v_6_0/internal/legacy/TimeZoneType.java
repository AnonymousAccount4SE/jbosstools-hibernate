package org.jboss.tools.hibernate.runtime.v_6_0.internal.legacy;

import java.util.TimeZone;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.TimeZoneJavaTypeDescriptor;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

public class TimeZoneType extends AbstractSingleColumnStandardBasicType<TimeZone> {

	public static final TimeZoneType INSTANCE = new TimeZoneType();

	public TimeZoneType() {
		super(VarcharJdbcType.INSTANCE, TimeZoneJavaTypeDescriptor.INSTANCE);
	}

	public String getName() {
		return "timezone";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}

}