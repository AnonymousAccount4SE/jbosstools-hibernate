package org.jboss.tools.hibernate.orm.runtime.exp.internal.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.tool.orm.jbt.wrp.Wrapper;
import org.jboss.tools.hibernate.runtime.common.IFacade;
import org.jboss.tools.hibernate.runtime.spi.IClassMetadata;
import org.jboss.tools.hibernate.runtime.spi.ICollectionMetadata;
import org.jboss.tools.hibernate.runtime.spi.IColumn;
import org.jboss.tools.hibernate.runtime.spi.IConfiguration;
import org.jboss.tools.hibernate.runtime.spi.ICriteria;
import org.jboss.tools.hibernate.runtime.spi.IJoin;
import org.jboss.tools.hibernate.runtime.spi.INamingStrategy;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IPrimaryKey;
import org.jboss.tools.hibernate.runtime.spi.IProperty;
import org.jboss.tools.hibernate.runtime.spi.IQuery;
import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringSettings;
import org.jboss.tools.hibernate.runtime.spi.IReverseEngineeringStrategy;
import org.jboss.tools.hibernate.runtime.spi.ISession;
import org.jboss.tools.hibernate.runtime.spi.ISessionFactory;
import org.jboss.tools.hibernate.runtime.spi.ITable;
import org.jboss.tools.hibernate.runtime.spi.IType;
import org.jboss.tools.hibernate.runtime.spi.IValue;

public class GenericFacadeFactory {
	
	public static IFacade createFacade(Class<?> facadeClass, Object target) {
		return (IFacade)Proxy.newProxyInstance(
					GenericFacadeFactory.class.getClassLoader(), 
					new Class[] { facadeClass, IFacade.class }, 
					new FacadeInvocationHandler(facadeClass, target));
	}
	
	private static class FacadeInvocationHandler implements InvocationHandler {
		
		private Object target = null;
		
		private FacadeInvocationHandler(Class<?> facadeClass, Object target) {
			FacadeInvocationHandler.this.target = target;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = null;
			if ("getTarget".equals(method.getName())) {
				result = target;
			} else if ("equals".equals(method.getName())) {
				return isEqual(proxy, args);
			} else {
				Class<?>[] argumentClasses = argumentClasses(args);
				Method targetMethod = ReflectUtil.lookupMethod(
						target.getClass(), 
						method.getName(), 
						argumentClasses);
				if (targetMethod != null) {
					try {
						result = ReflectUtil.invokeMethod(targetMethod, target, unwrapFacades(args));
					} catch (InvocationTargetException e) {
						throw e.getCause();
					}
					if (result != null) {
						Class<?> returnedClass = method.getReturnType();
						Type genericReturnType = method.getGenericReturnType();
						if (genericReturnType != null && genericReturnType instanceof ParameterizedType) {
							if (Iterator.class.isAssignableFrom(returnedClass) ) {
								result = createIteratorResult(
										(Iterator<?>)result, 
										(ParameterizedType)genericReturnType);
							}
							else if (Map.class.isAssignableFrom(returnedClass)) {
								result = createMapResult(
										(Map<?,?>)result,
										(ParameterizedType)genericReturnType);
							}
						}
						else if (returnedClass.isArray() && classesSet.contains(returnedClass.getComponentType())) {
							result = createArrayResult(
									(Object[])result,
									returnedClass.getComponentType());

						}
						else if (classesSet.contains(returnedClass)) {
							if (result == target) {
								result = proxy;
							} else {
								result = createFacade(returnedClass, result);
							}
						} 
					}
				} else {
					throw new RuntimeException(
							"Method '" + 
							target.getClass().getName() + "#" +
							method.getName() + 
							parameterTypes(argumentClasses) +
							" cannot be found.");
							
				}
			}
			return result;
		}
		
	}
	
	private static boolean isEqual(Object proxy, Object[] args) {
		if (args.length != 1) return false;
		if (!Proxy.isProxyClass(args[0].getClass())) return false;
		InvocationHandler right = Proxy.getInvocationHandler(args[0]);
		if (!(right instanceof FacadeInvocationHandler)) return false;
		InvocationHandler left = Proxy.getInvocationHandler(proxy);
		Object leftTarget = ((FacadeInvocationHandler)left).target;
		Object rightTarget = ((FacadeInvocationHandler)right).target;
		return leftTarget.equals(rightTarget);
	}
	
	private static Object[] unwrapFacades(Object[] args) {
		Object[] result = null;
		if (args != null) {
			result = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				if (IFacade.class.isAssignableFrom(args[i].getClass())) {
					Object target = ((IFacade)args[i]).getTarget();
					Class<?> targetClass = target.getClass();
					if (Proxy.isProxyClass(targetClass)) {
						targetClass = targetClass.getInterfaces()[0];
					}
					if (Wrapper.class.isAssignableFrom(targetClass)) {
						target = ((Wrapper)target).getWrappedObject();
					}
					result[i] = target;
				} else {
					result[i] = args[i];
				}
			}
		}
		return result;
 	}
	
	private static Iterator<?> createIteratorResult(Iterator<?> targetIterator, ParameterizedType parameterizedType) {
		Class<?> actualType = (Class<?>)parameterizedType.getActualTypeArguments()[0];
		boolean actualTypeIsFacade = classesSet.contains(actualType);
		return new Iterator<Object>() {
			@Override
			public boolean hasNext() {
				return targetIterator.hasNext();
			}
			@Override
			public Object next() {
				Object result = targetIterator.next();
				if (actualTypeIsFacade) result = createFacade(actualType, result);
				return result;
			}
			
		};		
	}	
	
	private static Map<?, ?> createMapResult(Map<?, ?> map, ParameterizedType parameterizedType) {
		Map<Object, Object> result = (Map<Object, Object>)map;
		Type actualValueType = parameterizedType.getActualTypeArguments()[1];
		if (actualValueType instanceof ParameterizedType && 
				List.class.isAssignableFrom((Class<?>)((ParameterizedType)actualValueType).getRawType())) {
			for (Object key : map.keySet()) {
				result.put(key, createListResult(
						(List<?>)map.get(key), 
						(ParameterizedType)actualValueType));
			}
			
		} else {
			Class<?> actualValueClass = (Class<?>)parameterizedType.getActualTypeArguments()[1];
			if  (classesSet.contains(actualValueClass)) {
				for (Object key : map.keySet()) {
					result.put(key, createFacade(actualValueClass, result.get(key)));
				}
			}
		}
		return result;
	}
	
	private static List<?> createListResult(List<?> list, ParameterizedType parameterizedType) {
		List<Object> result = (List<Object>)list;
		Class<?> actualValueClass = (Class<?>)parameterizedType.getActualTypeArguments()[0];
		if (classesSet.contains(actualValueClass)) {
			for (int i = 0; i < result.size(); i++) {
				result.set(i, createFacade(actualValueClass, result.get(i)));
			}
		}
		return result;
	}
	
	private static <T> T[] createArrayResult(Object[] array, Class<?> actualType) {
		T[] result = (T[])Array.newInstance(actualType, array.length);
		for (int i = 0; i < array.length; i++) {
			result[i] = (T)createFacade(actualType, array[i]);
		}
		return result;
	}
	
	private static Class<?>[] argumentClasses(Object[] args) {
		Class<?>[] result = new Class<?>[0];
		if (args != null) {
			result = new Class<?>[args.length];
			for (int i = 0; i < args.length; i++) {
				if ((args[i] == null)) continue;
				Class<?> argClass = args[i].getClass();
				if (IFacade.class.isAssignableFrom(argClass)) {
					Object target = ((IFacade)args[i]).getTarget();
					argClass = target.getClass();
					if (Proxy.isProxyClass(argClass)) {
						argClass = argClass.getInterfaces()[0];
					}
					if (Wrapper.class.isAssignableFrom(argClass)) {
						argClass = ((Wrapper)target).getWrappedObject().getClass();
					}
				}
				result[i] = argClass;
			}
		}
		return result;
	}
	
	private static String parameterTypes(Class<?>[] classes) {
		StringBuffer sb = new StringBuffer("(");
		for (Class<?> c : classes) {
			sb.append(c == null ? c : c.getSimpleName()).append(",");
		}
		if (sb.charAt(sb.length() - 1) == ',') {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(")");
		return sb.toString();
	}
	
	private static Set<Class<?>> classesSet = new HashSet<>(
			Arrays.asList(new Class[] {
					IClassMetadata.class,
					ICollectionMetadata.class,
					IColumn.class,
					IConfiguration.class,
					ICriteria.class,
					IJoin.class,
					INamingStrategy.class,
					IPersistentClass.class,
					IPrimaryKey.class,
					IProperty.class,
					IQuery.class,
					IReverseEngineeringStrategy.class,
					IReverseEngineeringSettings.class,
					ISession.class,
					ISessionFactory.class,
					ITable.class,
					IType.class,
					IValue.class
			}));
	
}
