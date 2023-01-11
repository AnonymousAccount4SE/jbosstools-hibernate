package org.jboss.tools.hibernate.runtime.v_6_2.internal.legacy;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.ClassJavaType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

public class ClassType extends AbstractSingleColumnStandardBasicType<Class> {
	public static final ClassType INSTANCE = new ClassType();

	public ClassType() {
		super(VarcharJdbcType.INSTANCE, ClassJavaType.INSTANCE);
	}

	public String getName() {
		return "class";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}

}
