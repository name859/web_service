package com.name859.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.name859.connection.DBConnection;

public class UserDao {

	private DBConnection dbc;
	
	public UserDao(DBConnection dbc) {
		this.dbc = dbc;
	}
	
	public boolean emailCheck(User user) {
		String query = "SELECT user_no, user_email, user_pw, user_name, user_time FROM user WHERE user_email = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean emailResult = false;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserEmail());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				emailResult = true;
			}
		} catch (SQLException se) {
			dbc.errSql(se);
		} finally {
			dbc.close(rs, pstmt, conn);
		}
		
		return emailResult;
	}
	
	public void userAdd(User user) {
		String query = "INSERT INTO user (user_email, user_pw, user_name, user_time) VALUES (?, ?, ?, NOW())";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserEmail());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			update(pstmt, conn);
		} catch (SQLException se) {
			dbc.errSql(se);
		}
	}
	
	public void userModify(User user) {
		String query = "UPDATE user SET user_email = ?, user_pw = ?, user_name = ? WHERE user_no = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user.getUserEmail());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			pstmt.setInt(4, user.getUserNo());
			update(pstmt, conn);
		} catch (SQLException se) {
			dbc.errSql(se);
		}
	}
	
	public void userDelete(User user) {
		String query = "DELETE FROM user WHERE user_no = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user.getUserNo());
			update(pstmt, conn);
		} catch (SQLException se) {
			dbc.errSql(se);
		}
	}
	
	public User userSearch(User user) {
		String query = "SELECT user_no, user_email, user_pw, user_name, user_time FROM user WHERE user_no = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, user.getUserNo());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user.setUserEmail(rs.getString("user_email"));
				user.setUserPw(rs.getString("user_pw"));
				user.setUserName(rs.getString("user_name"));
				user.setUserTime(rs.getString("user_time"));
			}
		} catch (SQLException se) {
			dbc.errSql(se);
		} finally {
			dbc.close(rs, pstmt, conn);
		}
		
		return user;
	}
	
	protected String userSearchOpt(String searchOpt) {
		String userSearchOpt = null;
		if (searchOpt.equals("ue")) {
			userSearchOpt = "user_email";
		} else if (searchOpt.equals("un")) {
			userSearchOpt = "user_name";
		}
		
		return userSearchOpt;
	}
	
	public int userSearchListCount(String searchOpt, String searchVal) {
		String query = "SELECT COUNT(user_no) FROM user WHERE "+ userSearchOpt(searchOpt) +" LIKE ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int userSearchListCount = 0;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+ searchVal +"%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userSearchListCount = rs.getInt("COUNT(user_no)");
			}
		} catch (SQLException se) {
			dbc.errSql(se);
		} finally {
			dbc.close(rs, pstmt, conn);
		}
		
		return userSearchListCount;
	}
	
	public List<User> userSearchList(String searchOpt, String searchVal, int searchPage, int viewRecord) {
		String query = "SELECT user_no, user_email, user_pw, user_name, user_time FROM user WHERE "+ userSearchOpt(searchOpt) +" LIKE ? LIMIT ?, ?";
		
		List<User> userSearchList = new ArrayList<User>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+ searchVal +"%");
			pstmt.setInt(2, (searchPage - 1) * viewRecord);
			pstmt.setInt(3, viewRecord);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUserNo(rs.getInt("user_no"));
				user.setUserEmail(rs.getString("user_email"));
				user.setUserPw(rs.getString("user_pw"));
				user.setUserName(rs.getString("user_name"));
				user.setUserTime(rs.getString("user_time"));
				userSearchList.add(user);
			}
		} catch (SQLException se) {
			dbc.errSql(se);
		} finally {
			dbc.close(rs, pstmt, conn);
		}
		
		return userSearchList;
	}
	
	public void userNoReset() {
		String query = "ALTER TABLE user AUTO_INCREMENT = 1";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbc.dbConnection();
			pstmt = conn.prepareStatement(query);
			update(pstmt, conn);
		} catch (SQLException se) {
			dbc.errSql(se);
		}
	}
	
	protected void update(PreparedStatement pstmt, Connection conn) {
		try {
			pstmt.executeUpdate();
		} catch (SQLException se) {
			dbc.errSql(se);
		} finally {
			dbc.close(null, pstmt, conn);
		}
	}
	
}
