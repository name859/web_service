package com.name859.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.name859.board.model.BoardConfig;
import com.name859.connection.DBConnection;
import com.name859.dao.DaoTemplate;
import com.name859.dao.MakePreparedStatement;
import com.name859.dao.MakeResultSet;

public class BoardConfigDao {

	private DaoTemplate daoTemplate;
	
	public BoardConfigDao(DBConnection dbc) {
		daoTemplate = new DaoTemplate(dbc);
	}
	
	public void boardConfigAdd(BoardConfig boardConfig) {
		daoTemplate.update(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "INSERT INTO board_config (board_id, board_name, board_config_time) VALUES (?, ?, NOW())";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, boardConfig.getBoardId());
					pstmt.setString(2, boardConfig.getBoardName());
					return pstmt;
				}
			}
		);
	}
	
	public void boardConfigModify(BoardConfig boardConfig) {
		daoTemplate.update(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "UPDATE board_config SET board_name = ? WHERE board_id = ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setString(1, boardConfig.getBoardName());
					pstmt.setInt(2, boardConfig.getBoardId());
					return pstmt;
				}
			}
		);
	}
	
	public void boardConfigDelete(BoardConfig boardConfig) {
		daoTemplate.update(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "DELETE FROM board_config WHERE board_id = ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, boardConfig.getBoardId());
					return pstmt;
				}
			}
		);
	}
	
	public BoardConfig boardConfigSearch(BoardConfig boardConfig) {
		return daoTemplate.<BoardConfig>query(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "SELECT board_id, board_name, board_config_time FROM board_config WHERE board_id = ?";
					PreparedStatement pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, boardConfig.getBoardId());
					return pstmt;
				}
			}
			, new MakeResultSet<BoardConfig>() {
				public BoardConfig makeRs(ResultSet rs) throws SQLException {
					if (rs.next()) {
						boardConfig.setBoardName(rs.getString("board_name"));
						boardConfig.setBoardConfigTime(rs.getString("board_config_time"));
					}
					return boardConfig;
				}
			}
		);
	}
	
	public List<BoardConfig> boardConfigSearchList() {
		return daoTemplate.<List<BoardConfig>>query(
			new MakePreparedStatement() {
				public PreparedStatement makePstmt(Connection conn) throws SQLException {
					String query = "SELECT board_id, board_name, board_config_time FROM board_config";
					PreparedStatement pstmt = conn.prepareStatement(query);
					return pstmt;
				}
			}
			, new MakeResultSet<List<BoardConfig>>() {
				public List<BoardConfig> makeRs(ResultSet rs) throws SQLException {
					List<BoardConfig> boardConfigSearchList = new ArrayList<BoardConfig>();
					BoardConfig boardConfig = null;
					while (rs.next()) {
						boardConfig = new BoardConfig();
						boardConfig.setBoardId(rs.getInt("board_id"));
						boardConfig.setBoardName(rs.getString("board_name"));
						boardConfig.setBoardConfigTime(rs.getString("board_config_time"));
						boardConfigSearchList.add(boardConfig);
					}
					return boardConfigSearchList;
				}
			}
		);
	}
	
}
