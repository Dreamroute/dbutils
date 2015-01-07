package com.changhong.dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbUtils {
	
	private static ThreadLocal<Connection> safeConn = new ThreadLocal<Connection>();
	
	// default constructor of none parameters
	public DbUtils() {}
	
	// get jdbc connection
	public static Connection getConnection() {
		Connection connection = safeConn.get();
		if(connection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dubbo?useUnicode=true&characterEncoding=utf-8", "root", "root");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		safeConn.set(connection);
		return connection;
	}
	
	// close conn
	public static void close(Connection conn) throws SQLException {
		if(conn != null) {
			conn.close();
		}
	}
	
	// close conn quietly
	public static void closeQuietly(Connection conn) {
		try {
			close(conn);
		} catch (SQLException e) {
			// do nothing
		}
	}
	
	// close resultSet
	public static void close(ResultSet rs) throws SQLException  {
		if(rs != null) {
			rs.close();
		}
	}
	
	// close resultSet quietly
	public static void closeQuietly(ResultSet rs) {
		try {
			close(rs);
		} catch (SQLException e) {
			// do nothing
		}
	}
	
	// close statement
	public static void close(Statement stmt) throws SQLException {
		if(stmt != null) {
			stmt.close();
		}
	}
	
	// close statement quietly
	public static void closeQuietly(Statement stmt) {
		try {
			close(stmt);
		} catch (SQLException e) {
			// do nothing
		}
	}
	
	// close result, statement and conn quietly
	public static void closeQuietly(Connection conn, Statement stmt, ResultSet rs) {
		try {
			closeQuietly(rs);
		} finally {
			try {
				closeQuietly(stmt);
			} finally {
				closeQuietly(conn);
			}
		}
	}
	
	// commit and close connection
	public static void commitAndClose(Connection conn) throws SQLException {
		if(conn != null) {
			try {
				conn.commit();
			} finally {
				conn.close();
			}
		}
	}
	
	// commit and close connection quietly
	public static void commitAndCloseQuietly(Connection conn) {
		try {
			commitAndClose(conn);
		} catch (SQLException e) {
			// do nothing
		}
	}
	
}
