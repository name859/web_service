package com.name859.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDBConnection extends DBConnection {

	public Connection dbConnection() {
		String driver = "org.mariadb.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/web_service";
		String user = "name859";
		String pw = "859";
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException cnfe) {
			errDriver(cnfe, driver);
		}
		
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pw);
		} catch (SQLException se) {
			errConnection(se);
		}
		
		return conn;
	}
	
}
