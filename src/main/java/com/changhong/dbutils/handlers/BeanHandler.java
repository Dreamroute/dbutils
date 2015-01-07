package com.changhong.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.changhong.dbutils.BasicRowProcessor;
import com.changhong.dbutils.RowProcessor;

public class BeanHandler<T> implements ResultSetHandler<T> {
	
	private Class<T> type;
	private RowProcessor convert;
	
	public BeanHandler() {}
	
	public BeanHandler(Class<T> type) {
		this(type, new BasicRowProcessor());
	}
	
	public BeanHandler(Class<T> type, RowProcessor convert) {
		this.type = type;
		this.convert = convert;
	}
	
	public T handle(ResultSet rs) throws SQLException {
		return rs.next() ? convert.toBean(rs, type) : null;
	}

}
