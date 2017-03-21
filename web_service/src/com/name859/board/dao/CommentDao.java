package com.name859.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.name859.board.model.Comment;
import com.name859.connection.DBConnection;
import com.name859.dao.DaoTemplate;
import com.name859.dao.MakePreparedStatement;
import com.name859.dao.MakeResultSet;

public class CommentDao {

	private DaoTemplate daoTemplate;
	
	public CommentDao(DBConnection dbc) {
		daoTemplate = new DaoTemplate(dbc);
	}
	
	public void commentAdd(Comment comment) {
		MakePreparedStatement mPstmt = new MakePreparedStatement() {
			public PreparedStatement makePstmt(Connection conn) throws SQLException {
				String query = "INSERT INTO comment (board_no, user_no, comment_writer, comment_content, comment_ip, comment_time) VALUES (?, ?, ?, ?, ?, NOW())";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, comment.getBoardNo());
				pstmt.setInt(2, comment.getUserNo());
				pstmt.setString(3, comment.getCommentWriter());
				pstmt.setString(4, comment.getCommentContent());
				pstmt.setString(5, comment.getCommentIp());
				return pstmt;
			}
		};
		
		daoTemplate.update(mPstmt);
	}
	
	public void commentModify(Comment comment) {
		MakePreparedStatement mPstmt = new MakePreparedStatement() {
			public PreparedStatement makePstmt(Connection conn) throws SQLException {
				String query = "UPDATE comment SET comment_writer = ?, comment_content = ?, comment_ip = ? WHERE comment_no = ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setString(1, comment.getCommentWriter());
				pstmt.setString(2, comment.getCommentContent());
				pstmt.setString(3, comment.getCommentIp());
				pstmt.setInt(4, comment.getCommentNo());
				return pstmt;
			}
		};
		
		daoTemplate.update(mPstmt);
	}
	
	public void commentDelete(Comment comment) {
		MakePreparedStatement mPstmt = new MakePreparedStatement() {
			public PreparedStatement makePstmt(Connection conn) throws SQLException {
				String query = "DELETE FROM comment WHERE comment_no = ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, comment.getCommentNo());
				return pstmt;
			}
		};
		
		daoTemplate.update(mPstmt);
	}
	
	public Comment commentSearch(Comment comment) {
		MakePreparedStatement mPstmt = new MakePreparedStatement() {
			public PreparedStatement makePstmt(Connection conn) throws SQLException {
				String query = "SELECT comment_no, board_no, user_no, comment_writer, comment_content, comment_ip, comment_time FROM comment WHERE comment_no = ?";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, comment.getCommentNo());
				return pstmt;
			}
		};
		
		MakeResultSet<Comment> mRs = new MakeResultSet<Comment>() {
			public Comment makeRs(ResultSet rs) throws SQLException {
				if (rs.next()) {
					comment.setBoardNo(rs.getInt("board_no"));
					comment.setUserNo(rs.getInt("user_no"));
					comment.setCommentWriter(rs.getString("comment_writer"));
					comment.setCommentContent(rs.getString("comment_content"));
					comment.setCommentIp(rs.getString("comment_ip"));
					comment.setCommentTime(rs.getString("comment_time"));
				}
				return comment;
			}
		};
		
		return daoTemplate.<Comment>query(mPstmt, mRs);
	}
	
	public List<Comment> commentSearchList(Comment comment) {
		MakePreparedStatement mPstmt = new MakePreparedStatement() {
			public PreparedStatement makePstmt(Connection conn) throws SQLException {
				String query = "SELECT comment_no, board_no, user_no, comment_writer, comment_content, comment_ip, comment_time FROM comment WHERE board_no = ? ORDER BY comment_no DESC";
				PreparedStatement pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, comment.getBoardNo());
				return pstmt;
			}
		};
		
		MakeResultSet<List<Comment>> mRs = new MakeResultSet<List<Comment>>() {
			public List<Comment> makeRs(ResultSet rs) throws SQLException {
				List<Comment> commentSearchList = new ArrayList<Comment>();
				Comment comment = null;
				while (rs.next()) {
					comment = new Comment();
					comment.setCommentNo(rs.getInt("comment_no"));
					comment.setBoardNo(rs.getInt("board_no"));
					comment.setUserNo(rs.getInt("user_no"));
					comment.setCommentWriter(rs.getString("comment_writer"));
					comment.setCommentContent(rs.getString("comment_content"));
					comment.setCommentIp(rs.getString("comment_ip"));
					comment.setCommentTime(rs.getString("comment_time"));
					commentSearchList.add(comment);
				}
				return commentSearchList;
			}
		};
		
		return daoTemplate.<List<Comment>>query(mPstmt, mRs);
	}
	
}
