package com.changhong.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.changhong.dbutils.handlers.ResultSetHandler;

public class QueryRunner extends AbstractQueryRunner {
	
	public QueryRunner() {
		super();
	}
	
	public <T> T query(Connection conn, boolean closeConn, String sql, ResultSetHandler<T> rsh, Object ... params) throws SQLException {
		
		if(null == conn) {
			throw new SQLException("null jdbc connection");
		}
		
		if(null == sql || "".equals(sql.trim())) {
			if(closeConn) {
				close(conn);
			}
			throw new SQLException("null sql Statement");
		}
		
		if(null == rsh) {
			if(closeConn) {
				close(conn);
			}
			throw new SQLException("null resulthandler");
		}
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		T result = null;
		
		try {
			stmt = this.PreparedStatement(conn, sql);
			this.fillStatement(stmt, params);
			rs = this.wrap(stmt.executeQuery());
			result = rsh.handle(rs);
			
			if(closeConn) {
				close(conn);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private PreparedStatement PreparedStatement(Connection conn, String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
}
