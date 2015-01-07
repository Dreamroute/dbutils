package com.changhong.dbutils;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


/**
 * QueryRunner abstract class
 * 
 * @author dehai.wang@changhong.com
 * @date 2014-12-09
 * @version 1.0
 *
 */
public abstract class AbstractQueryRunner {

	public AbstractQueryRunner() {}
	
	protected void close(Connection conn) throws SQLException {
		if(null != conn) {
			DbUtils.close(conn);
		}
	}
	
	protected void fillStatement(PreparedStatement stmt, Object[] params) throws SQLException {
		
		ParameterMetaData pmd = stmt.getParameterMetaData();
		
		int stmtCount = pmd.getParameterCount();
		int paramsCount = null == params ? 0 : params.length;
		
		if(stmtCount != paramsCount) {
			throw new SQLException("The number of params is error, expected " + stmtCount + ", but given " + paramsCount);
		}
		
		for(int i = 1; i <= stmtCount; i++) {
			Object param = params[i -1];
			
			if(null != param) {
				stmt.setObject(i, param);
			} else {
				int sqlType = Types.VARCHAR;
				stmt.setNull(i, sqlType);
			}
		}
	}
	
	protected ResultSet wrap(ResultSet rs) {
		return rs;
	}
	
}




