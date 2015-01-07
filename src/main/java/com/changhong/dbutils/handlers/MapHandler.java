package com.changhong.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.changhong.dbutils.RowProcessor;

public class MapHandler implements ResultSetHandler<Map<String, Object>>{
	
	private RowProcessor convert;
	
	public MapHandler(RowProcessor convert) {
		this.convert = convert;
	}
	
	public MapHandler() {
		this(ArrayHandler.ROW_PROCESSOR);
	}

	public Map<String, Object> handle(ResultSet rs) throws SQLException {
		return rs.next()? convert.toMap(rs) : null;
	}

}
