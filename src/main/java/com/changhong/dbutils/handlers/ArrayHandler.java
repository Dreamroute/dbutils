package com.changhong.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.changhong.dbutils.BasicRowProcessor;
import com.changhong.dbutils.RowProcessor;

public class ArrayHandler implements ResultSetHandler<Object[]>{
	
	static final RowProcessor ROW_PROCESSOR = new BasicRowProcessor();
	
	private RowProcessor convert;
	
	public ArrayHandler(RowProcessor convert) {
		this.convert = convert;
	}
	
	public ArrayHandler() {
		this(ROW_PROCESSOR);
	}

	public Object[] handle(ResultSet rs) throws SQLException {
		return rs.next()? convert.toArray(rs) : null;
	}

}
