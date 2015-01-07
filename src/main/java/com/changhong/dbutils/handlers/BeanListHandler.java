package com.changhong.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.changhong.dbutils.BasicRowProcessor;
import com.changhong.dbutils.RowProcessor;

public class BeanListHandler<T> implements ResultSetHandler<List<T>> {
	
	private Class<T> type;
	private RowProcessor convert;
	
	public BeanListHandler(Class<T> type, RowProcessor convert) {
		this.type = type;
		this.convert = convert;
	}
	
	public BeanListHandler(Class<T> type) {
		this(type, new BasicRowProcessor());
	}
	
	public List<T> handle(ResultSet rs) throws SQLException {
		return rs.next()? convert.toBeanList(rs, type) : null;
	}
	
}
