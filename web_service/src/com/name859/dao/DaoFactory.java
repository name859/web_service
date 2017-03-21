package com.name859.dao;

import com.name859.board.dao.BoardConfigDao;
import com.name859.board.dao.BoardDao;
import com.name859.board.dao.CommentDao;
import com.name859.connection.DBConnection;
import com.name859.connection.LocalDBConnection;
import com.name859.login.LoginDao;
import com.name859.user.UserDao;

public class DaoFactory {

	private DBConnection dbc = new LocalDBConnection();
	
	public UserDao getUserDao() {
		return new UserDao(dbc);
	}
	
	public LoginDao getLoginDao() {
		return new LoginDao(dbc);
	}
	
	public BoardDao getBoardDao() {
		return new BoardDao(dbc);
	}
	
	public BoardConfigDao getBoardConfigDao() {
		return new BoardConfigDao(dbc);
	}
	
	public CommentDao getCommentDao() {
		return new CommentDao(dbc);
	}
	
}
