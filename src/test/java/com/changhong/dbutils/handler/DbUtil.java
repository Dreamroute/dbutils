package com.changhong.dbutils.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dubbo?useUnicode=true&characterEncoding=utf-8", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void CloseConnection(Connection connection) {
		try {
			if(connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
