package com.changhong.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RowProcessor {
	<T> T toBean(ResultSet rs, Class<T> type) throws SQLException;

	<T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException;

	Object[] toArray(ResultSet rs) throws SQLException;
	
	Map<String, Object> toMap(ResultSet rs) throws SQLException;
	
	//List<Object[]> toArrayList(ResultSet rs) throws SQLException;
}
