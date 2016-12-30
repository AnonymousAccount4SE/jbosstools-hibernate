package org.jboss.tools.hibernate.runtime.spi;

import java.util.Iterator;
import java.util.Properties;

public interface IValue {

	boolean isSimpleValue();
	boolean isCollection();
	IValue getCollectionElement();
	boolean isOneToMany();
	boolean isManyToOne();
	boolean isOneToOne();
	boolean isMap();
	boolean isComponent();
	Boolean isEmbedded();
	boolean isToOne();
	ITable getTable();
	IType getType();
	void setElement(IValue element);
	void setCollectionTable(ITable table);
	void setTable(ITable table);
	boolean isList();
	void setIndex(IValue value);
	void setTypeName(String name);
	String getComponentClassName();
	Iterator<IColumn> getColumnIterator();
	Boolean isTypeSpecified();
	ITable getCollectionTable();
	IValue getKey();
	IValue getIndex();
	String getElementClassName();
	String getTypeName();
	boolean isDependantValue();
	boolean isAny();
	boolean isSet();
	boolean isPrimitiveArray();
	boolean isArray();
	boolean isIdentifierBag();
	boolean isBag();
	String getReferencedEntityName();
	String getEntityName();
	Iterator<IProperty> getPropertyIterator();
	void addColumn(IColumn column);
	void setTypeParameters(Properties typeParameters);
	String getForeignKeyName();
	IPersistentClass getOwner();
	IValue getElement();
	String getParentProperty();
	void setElementClassName(String binaryName);
	void setKey(IValue keyValue);
	void setFetchModeJoin();
	boolean isInverse();
	IPersistentClass getAssociatedClass();
	void setLazy(boolean b);
	void setRole(String role);
	void setReferencedEntityName(String name);
	void setAssociatedClass(IPersistentClass persistentClass);

}
