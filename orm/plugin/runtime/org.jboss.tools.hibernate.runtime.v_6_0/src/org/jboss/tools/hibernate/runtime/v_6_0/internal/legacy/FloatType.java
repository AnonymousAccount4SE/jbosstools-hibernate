package org.jboss.tools.hibernate.runtime.v_6_0.internal.legacy;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.FloatTypeDescriptor;
import org.hibernate.type.descriptor.jdbc.FloatJdbcType;

public class FloatType extends AbstractSingleColumnStandardBasicType<Float> {
	public static final FloatType INSTANCE = new FloatType();


	public FloatType() {
		super( FloatJdbcType.INSTANCE, FloatTypeDescriptor.INSTANCE );
	}
	@Override
	public String getName() {
		return "float";
	}

	@Override
	public String[] getRegistrationKeys() {
		return new String[] { getName(), float.class.getName(), Float.class.getName() };
	}
}
