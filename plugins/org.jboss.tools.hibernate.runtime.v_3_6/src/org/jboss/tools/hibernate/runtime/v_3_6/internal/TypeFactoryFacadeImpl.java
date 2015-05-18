package org.jboss.tools.hibernate.runtime.v_3_6.internal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.jboss.tools.hibernate.runtime.common.AbstractTypeFactoryFacade;
import org.jboss.tools.hibernate.runtime.spi.IFacadeFactory;
import org.jboss.tools.hibernate.runtime.spi.ITable;
import org.jboss.tools.hibernate.runtime.spi.IType;

public class TypeFactoryFacadeImpl extends AbstractTypeFactoryFacade {
	
	private Map<IType, String> typeFormats = null;
	private BasicTypeRegistry typeRegistry = new BasicTypeRegistry();

	public TypeFactoryFacadeImpl(IFacadeFactory facadeFactory, Object target) {
		super(facadeFactory, target);
	}

	@Override
	public IType getTimestampType() {
		return getFacadeFactory().createType(StandardBasicTypes.TIMESTAMP);
	}

	@Override
	public IType getTimezoneType() {
		return getFacadeFactory().createType(StandardBasicTypes.TIMEZONE);
	}

	@Override
	public IType getTrueFalseType() {
		return getFacadeFactory().createType(StandardBasicTypes.TRUE_FALSE);
	}

	@Override
	public IType getYesNoType() {
		return getFacadeFactory().createType(StandardBasicTypes.YES_NO);
	}

	@Override
	public IType getNamedType(String typeName) {
		return getFacadeFactory().createType(typeRegistry.getRegisteredType(typeName));
	}

	@Override
	public IType getBasicType(String typeName) {
		return getNamedType(typeName);
	}

	@Override
	public Map<IType, String> getTypeFormats() {
		if (typeFormats == null) {
			initializeTypeFormats();
		}
		return typeFormats;
	}
	
	private void initializeTypeFormats() {
		typeFormats = new HashMap<IType, String>();		
		addTypeFormat(getBooleanType(), Boolean.TRUE);
		addTypeFormat(getByteType(), Byte.valueOf((byte) 42));
		addTypeFormat(getBigIntegerType(), BigInteger.valueOf(42));
		addTypeFormat(getShortType(), Short.valueOf((short) 42));
		addTypeFormat(getCalendarType(), new GregorianCalendar());
		addTypeFormat(getCalendarDateType(), new GregorianCalendar());
		addTypeFormat(getIntegerType(), Integer.valueOf(42));
		addTypeFormat(getBigDecimalType(), new BigDecimal(42.0));
		addTypeFormat(getCharacterType(), Character.valueOf('h'));
		addTypeFormat(getClassType(), ITable.class);
		addTypeFormat(getCurrencyType(), Currency.getInstance(Locale.getDefault()));
		addTypeFormat(getDateType(), new Date());
		addTypeFormat(getDoubleType(), Double.valueOf(42.42));
		addTypeFormat(getFloatType(), Float.valueOf((float)42.42));
		addTypeFormat(getLocaleType(), Locale.getDefault());
		addTypeFormat(getLongType(), Long.valueOf(42));
		addTypeFormat(getStringType(), "a string"); //$NON-NLS-1$
		addTypeFormat(getTextType(), "a text"); //$NON-NLS-1$
		addTypeFormat(getTimeType(), new Date());
		addTypeFormat(getTimestampType(), new Date());
		addTypeFormat(getTimezoneType(), TimeZone.getDefault());
		addTypeFormat(getTrueFalseType(), Boolean.TRUE);
		addTypeFormat(getYesNoType(), Boolean.TRUE);
	}
	
	private void addTypeFormat(IType type, Object value) {
		typeFormats.put(type, type.toString(value));
	}
}
