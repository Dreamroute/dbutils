package com.changhong.dbutils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasicRowProcessor implements RowProcessor {
	
	private BeanProcessor convert = new BeanProcessor();
	
	public BasicRowProcessor() {}
	
	public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
		return convert.toBean(rs, type);
	}

	public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
		return convert.toBeanList(rs, type);
	}
	
	public Object[] toArray(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Object[] result = new Object[columnCount];
		for(int i=0; i<columnCount; i++) {
			result[i] = rs.getObject(i + 1);
		}
		return result;
	}

	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		for(int i=1; i<=rsmd.getColumnCount(); i++) {
			String columnName = rsmd.getColumnLabel(i);
			if(null == columnName || "".equals(columnName)) {
				columnName = rsmd.getColumnLabel(i);
			}
			Object value = rs.getObject(i);
			result.put(columnName, value);
		}
		return result;
	}

}
