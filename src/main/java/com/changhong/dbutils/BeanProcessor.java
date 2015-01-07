package com.changhong.dbutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BeanProcessor {
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		PropertyDescriptor[] props = this.propertyDescriptors(type);
		
		int[] columToProperty = this.mapColumnsToProperties(rsmd, props);
		
		 T result = this.createBean(rs, props, columToProperty, type);
		
		return result;
	}
	
	public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
		List<T> result = new ArrayList<T>();
		T bean = null;
		do{
			bean = this.toBean(rs, type);
			result.add(bean);
		} while(rs.next());
		return result;
	}
	
	private <T> T createBean(ResultSet rs, PropertyDescriptor[] props, int[] columToProperty, Class<T> type) throws SQLException {
		T bean = null;
		
		bean = this.newInstance(type, bean);
		
		for(int i=1; i<columToProperty.length; i++) {
			if(columToProperty[i] == -1) {
				continue;
			}
			
			PropertyDescriptor prop = props[columToProperty[i]];
			Class<?> propType = prop.getPropertyType();
			Object value = this.getValueOfResultSet(propType, i, rs);
			this.callSetter(prop, bean, value);
		}
		
		return bean;
	}

	private void callSetter(PropertyDescriptor prop, Object target, Object value) throws SQLException {
		Method setter = prop.getWriteMethod();
		if(null == setter) {
			return;
		}
		
		Class<?>[] paramTypes = setter.getParameterTypes();
		
		if(null == paramTypes || paramTypes.length != 1) {
			throw new SQLException("The number of setter's param is error, expected 1 but 0 or more, check the property: " + prop.getName() + " !");
		}
		
		try {
			setter.invoke(target, value);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private <T> T newInstance(Class<T> type, T bean) {
		try {
			bean = type.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	private Object getValueOfResultSet(Class<?> paramType, int index, ResultSet rs) throws SQLException {
		Object value = null;
		
		if(!paramType.isPrimitive() && null == rs.getObject(index)) {
			return null;
		}
		
		if(paramType == String.class) {
			value = rs.getString(index);
		} else if(paramType == Integer.class || paramType == int.class) {
			value = rs.getInt(index);
		} else if(paramType == Float.class || paramType == float.class) {
			value = rs.getFloat(index);
		} else if(paramType == Long.class || paramType == long.class) {
			value = rs.getLong(index);
		} else if(paramType == char.class || paramType == Character.class) {
			value = rs.getCharacterStream(index);
		} else if(paramType == short.class || paramType == Short.class) {
			value = rs.getCharacterStream(index);
		} else if(paramType == boolean.class || paramType == Boolean.class) {
			value = rs.getBoolean(index);
		} else if(paramType == byte.class || paramType == Byte.class) {
			value = rs.getByte(index);
		} else if(paramType == double.class || paramType == Double.class) {
			value = rs.getDouble(index);
		} else if(paramType == Date.class) {
			value = rs.getDate(index);
		}
		
		return value;
	}

	private int[] mapColumnsToProperties(ResultSetMetaData rsmd, PropertyDescriptor[] props) throws SQLException {
		
		int[] columnToProperty = new int[rsmd.getColumnCount() + 1];
		Arrays.fill(columnToProperty, -1);
		
		for(int i=1; i<columnToProperty.length; i++) {
			String columnLabel = rsmd.getColumnLabel(i);
			if(null == columnLabel || "".equals(columnLabel)) {
				columnLabel = rsmd.getColumnClassName(i);
			}
			for(int j=0; j<props.length; j++) {
				String propName = props[j].getName();
				if(columnLabel.equalsIgnoreCase(propName)) {
					columnToProperty[i] = j;
					break;
				}
			}
		}
		
		return columnToProperty;
	}
	
	private <T> PropertyDescriptor[] propertyDescriptors(Class<T> type) throws SQLException {
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(type);
		} catch (IntrospectionException e) {
			throw new SQLException("bean error!");
		}
		return info.getPropertyDescriptors();
	}
}
