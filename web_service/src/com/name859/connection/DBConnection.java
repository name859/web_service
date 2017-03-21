package com.name859.connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DBConnection {

	public abstract Connection dbConnection();
	
	public void close(ResultSet rs, Statement stmt, Connection conn) {
		try { if (rs != null) rs.close(); } catch (SQLException se) { errClose(se); }
		try { if (stmt != null) stmt.close(); } catch (SQLException se) { errClose(se); }
		try { if (conn != null) conn.close(); } catch (SQLException se) { errClose(se); }
	}
	
	protected void errDriver(Exception e, String driver) {
		System.out.println("@ Driver Not Found! ["+ driver +"]");
		e.printStackTrace();
	}
	
	protected void errConnection(Exception e) {
		System.out.println("@ DB Connection Error!");
		e.printStackTrace();
	}
	
	public void errSql(Exception e) {
		System.out.println("@ SQL Runtime Error!");
		e.printStackTrace();
	}
	
	public void errClose(Exception e) {
		System.out.println("@ DB Close Error!");
		e.printStackTrace();
	}
	
}
