package com.name859.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.name859.connection.DBConnection;
import com.name859.dao.DaoTemplate;
import com.name859.dao.MakePreparedStatement;
import com.name859.dao.MakeResultSet;
import com.name859.user.User;

public class LoginDao {

	private DaoTemplate daoTemplate;
	
	public LoginDao(DBConnection dbc) {
		daoTemplate = new DaoTemplate(dbc);
	}
	
	public boolean loginCheck(User user) {
		return daoTemplate.<Boolean>query(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "SELECT user_no, user_email, user_pw, user_name, user_time FROM user WHERE user_email = ? AND user_pw = ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, user.getUserEmail());
					pstmt.setString(2, user.getUserPw());
					return pstmt;
				}
			}
			, new MakeResultSet<Boolean>() {
				public Boolean makeRs(ResultSet rs) throws SQLException {
					boolean loginResult = false;
					if (rs.next()) {
						loginResult = true;
						
						user.setUserNo(rs.getInt("user_no"));
						user.setUserName(rs.getString("user_name"));
						user.setUserTime(rs.getString("user_time"));
					}
					return loginResult;
				}
			}
		);
	}
	
}
