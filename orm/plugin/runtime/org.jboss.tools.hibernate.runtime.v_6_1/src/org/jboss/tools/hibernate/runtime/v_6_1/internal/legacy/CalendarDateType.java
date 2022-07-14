package org.jboss.tools.hibernate.runtime.v_6_1.internal.legacy;

import java.util.Calendar;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.java.CalendarDateJavaType;
import org.hibernate.type.descriptor.jdbc.DateJdbcType;

public class CalendarDateType extends AbstractSingleColumnStandardBasicType<Calendar> {
	public static final CalendarDateType INSTANCE = new CalendarDateType();

	public CalendarDateType() {
		super(DateJdbcType.INSTANCE, CalendarDateJavaType.INSTANCE);
	}

	public String getName() {
		return "calendar_date";
	}

}
