package com.changhong.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.changhong.dbutils.RowProcessor;

public class MapListHandler extends AbstractListHandler<Map<String, Object>>{
	
	private RowProcessor convert;
	
	public MapListHandler() {
		this(ArrayHandler.ROW_PROCESSOR);
	}
	
	public MapListHandler(RowProcessor convert) {
		this.convert = convert;
	}

	@Override
	public Map<String, Object> handlerRow(ResultSet rs) throws SQLException {
		return convert.toMap(rs);
	}
	
}
