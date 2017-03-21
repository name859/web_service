package com.name859.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.name859.connection.DBConnection;

public class DaoTemplate {

	private DBConnection dbc;
	
	public DaoTemplate(DBConnection dbc) {
		this.dbc = dbc;
	}
	
	public void update(MakePreparedStatement makePstmt) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbc.dbConnection();
			pstmt = makePstmt.makePstmt(conn);
			pstmt.executeUpdate();
		} catch (SQLException se) {
			dbc.errSql(se);
		} finally {
			dbc.close(null, pstmt, conn);
		}
	}
	
	public <T> T query(MakePreparedStatement mPstmt, MakeResultSet<T> mRs) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T t = null;
		try {
			conn = dbc.dbConnection();
			pstmt = mPstmt.makePstmt(conn);
			rs = pstmt.executeQuery();
			t = mRs.makeRs(rs);
		} catch (SQLException se) {
			dbc.errSql(se);
		} finally {
			dbc.close(rs, pstmt, conn);
		}
		
		return t;
	}
	
}
