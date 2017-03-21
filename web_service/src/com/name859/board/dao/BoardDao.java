package com.name859.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.name859.board.model.Board;
import com.name859.connection.DBConnection;
import com.name859.dao.DaoTemplate;
import com.name859.dao.MakePreparedStatement;
import com.name859.dao.MakeResultSet;

public class BoardDao {

	private DBConnection dbc;
	private DaoTemplate daoTemplate;
	
	public BoardDao(DBConnection dbc) {
		this.dbc = dbc;
		daoTemplate = new DaoTemplate(dbc);
	}
	
	public void boardAdd(Board board) {
		daoTemplate.update(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "INSERT INTO board (board_id, user_no, board_writer, board_pw, board_subject, board_content, board_file_name, board_file_rename, board_file_size, board_ip, board_hit, board_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, NOW())";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, board.getBoardId());
					pstmt.setInt(2, board.getUserNo());
					pstmt.setString(3, board.getBoardWriter());
					pstmt.setString(4, board.getBoardPw());
					pstmt.setString(5, board.getBoardSubject());
					pstmt.setString(6, board.getBoardContent());
					pstmt.setString(7, board.getBoardFileName());
					pstmt.setString(8, board.getBoardFileRename());
					pstmt.setLong(9, board.getBoardFileSize());
					pstmt.setString(10, board.getBoardIp());
					return pstmt;
				}
			}
		);
	}
	
	public void boardModify(Board board) {
		daoTemplate.update(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "UPDATE board SET board_writer = ?, board_pw = ?, board_subject = ?, board_content = ?, board_file_name = ?, board_file_rename = ?, board_file_size = ?, board_ip = ? WHERE board_no = ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, board.getBoardWriter());
					pstmt.setString(2, board.getBoardPw());
					pstmt.setString(3, board.getBoardSubject());
					pstmt.setString(4, board.getBoardContent());
					pstmt.setString(5, board.getBoardFileName());
					pstmt.setString(6, board.getBoardFileRename());
					pstmt.setLong(7, board.getBoardFileSize());
					pstmt.setString(8, board.getBoardIp());
					pstmt.setInt(9, board.getBoardNo());
					return pstmt;
				}
			}
		);
	}
	
	public boolean boardDelete(Board board) {
		String commentDelete = "DELETE FROM comment WHERE board_no = ?";
		String boardDelete = "DELETE FROM board WHERE board_no = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean deleteResult = false;
		try {
			conn = dbc.dbConnection();
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(commentDelete);
			pstmt.setInt(1, board.getBoardNo());
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(boardDelete);
			pstmt.setInt(1, board.getBoardNo());
			pstmt.executeUpdate();
			
			conn.commit();
			
			deleteResult = true;
		} catch (SQLException sqle) {
			try { conn.rollback(); } catch (SQLException se) { dbc.errSql(se); }
			
			dbc.errSql(sqle);
		} finally {
			try { conn.setAutoCommit(true); } catch (SQLException se) { dbc.errSql(se); }
			
			dbc.close(null, pstmt, conn);
		}
		
		return deleteResult;
	}
	
	public void boardHitPlus(Board board) {
		daoTemplate.update(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "UPDATE board SET board_hit = board_hit + 1 WHERE board_no = ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, board.getBoardNo());
					return pstmt;
				}
			}
		);
	}
	
	public Board boardSearch(Board board) {
		return daoTemplate.query(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					StringBuffer query = new StringBuffer();
					query.append("SELECT board_no, board_id, user_no, board_writer, board_pw, board_subject, board_content, board_file_name, board_file_rename, board_file_size, board_ip, board_hit, board_time ")
					.append(", (SELECT COUNT(comment_no) FROM comment WHERE board.board_no = comment.board_no) AS comment_count ")
					.append("FROM board ")
					.append("WHERE board_no = ?");
					PreparedStatement pstmt = conn.prepareStatement(query.toString());
					pstmt.setInt(1, board.getBoardNo());
					return pstmt;
				}
			}
			, new MakeResultSet<Board>() {
				public Board makeRs(ResultSet rs) throws SQLException {
					if (rs.next()) {
						board.setBoardId(rs.getInt("board_id"));
						board.setUserNo(rs.getInt("user_no"));
						board.setBoardWriter(rs.getString("board_writer"));
						board.setBoardPw(rs.getString("board_pw"));
						board.setBoardSubject(rs.getString("board_subject"));
						board.setBoardContent(rs.getString("board_content"));
						board.setBoardFileName(rs.getString("board_file_name"));
						board.setBoardFileRename(rs.getString("board_file_rename"));
						board.setBoardFileSize(rs.getLong("board_file_size"));
						board.setBoardIp(rs.getString("board_ip"));
						board.setBoardHit(rs.getInt("board_hit"));
						board.setBoardTime(rs.getString("board_time"));
						board.setCommentCount(rs.getInt("comment_count"));
					}
					return board;
				}
			}
		);
	}
	
	protected String boardSearchOpt(String searchOpt) {
		String boardSearchOpt = null;
		if (searchOpt.equals("bs")) {
			boardSearchOpt = "board_subject";
		} else if (searchOpt.equals("bc")) {
			boardSearchOpt = "board_content";
		} else if (searchOpt.equals("bw")) {
			boardSearchOpt = "board_writer";
		}
		return boardSearchOpt;
	}
	
	public int boardSearchListCount(Board board, String searchOpt, String searchVal) {
		return daoTemplate.query(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "SELECT COUNT(board_no) FROM board WHERE board_id = ? AND "+ boardSearchOpt(searchOpt) +" LIKE ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, board.getBoardId());
					pstmt.setString(2, "%"+ searchVal +"%");
					return pstmt;
				}
			}
			, new MakeResultSet<Integer>() {
				public Integer makeRs(ResultSet rs) throws SQLException {
					int boardSearchListCount = 0;
					if (rs.next()) {
						boardSearchListCount = rs.getInt("COUNT(board_no)");
					}
					return boardSearchListCount;
				}
			}
		);
	}
	
	public List<Board> boardSearchList(Board board, String searchOpt, String searchVal, int searchPage, int viewRecord) {
		return daoTemplate.query(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					StringBuffer sb = new StringBuffer();
					sb.append("SELECT board_no, board_id, user_no, board_writer, board_pw, board_subject, board_content, board_file_name, board_file_rename, board_file_size, board_ip, board_hit, board_time ")
					.append(", (SELECT COUNT(comment_no) FROM comment WHERE board.board_no = comment.board_no) AS comment_count ")
					.append("FROM board ")
					.append("WHERE board_id = ? AND ");
					String query = sb.toString() + boardSearchOpt(searchOpt) +" LIKE ? ORDER BY board_no DESC LIMIT ?, ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, board.getBoardId());
					pstmt.setString(2, "%"+ searchVal +"%");
					pstmt.setInt(3, (searchPage - 1) * viewRecord);
					pstmt.setInt(4, viewRecord);
					return pstmt;
				}
			}
			, new MakeResultSet<List<Board>>() {
				public List<Board> makeRs(ResultSet rs) throws SQLException {
					List<Board> boardSearchList = new ArrayList<Board>();
					Board board = null;
					while (rs.next()) {
						board = new Board();
						board.setBoardNo(rs.getInt("board_no"));
						board.setBoardId(rs.getInt("board_id"));
						board.setUserNo(rs.getInt("user_no"));
						board.setBoardWriter(rs.getString("board_writer"));
						board.setBoardPw(rs.getString("board_pw"));
						board.setBoardSubject(rs.getString("board_subject"));
						board.setBoardContent(rs.getString("board_content"));
						board.setBoardFileName(rs.getString("board_file_name"));
						board.setBoardFileRename(rs.getString("board_file_rename"));
						board.setBoardFileSize(rs.getLong("board_file_size"));
						board.setBoardIp(rs.getString("board_ip"));
						board.setBoardHit(rs.getInt("board_hit"));
						board.setBoardTime(rs.getString("board_time"));
						board.setCommentCount(rs.getInt("comment_count"));
						boardSearchList.add(board);
					}
					return boardSearchList;
				}
			}
		);
	}
	
}
