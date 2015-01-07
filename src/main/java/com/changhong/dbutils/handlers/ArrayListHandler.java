package com.changhong.dbutils.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.changhong.dbutils.RowProcessor;

public class ArrayListHandler extends AbstractListHandler<Object[]> {
	
	private RowProcessor convert;
	
	public ArrayListHandler() {
		this(ArrayHandler.ROW_PROCESSOR);
	}
	
	private ArrayListHandler(RowProcessor convert) {
		super();
		this.convert = convert;
	}
	
	@Override
	public Object[] handlerRow(ResultSet rs) throws SQLException {
		return convert.toArray(rs);
	}

}
