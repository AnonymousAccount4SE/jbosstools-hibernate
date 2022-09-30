package org.jboss.tools.hibernate.orm.runtime.exp.internal.legacy;

import java.util.Locale;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.LocaleJavaType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

public class LocaleType extends AbstractSingleColumnStandardBasicType<Locale> {

	public static final LocaleType INSTANCE = new LocaleType();

	public LocaleType() {
		super( VarcharJdbcType.INSTANCE, LocaleJavaType.INSTANCE );
	}

	public String getName() {
		return "locale";
	}

	@Override
	protected boolean registerUnderJavaType() {
		return true;
	}

}
