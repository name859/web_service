package com.name859.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface MakePreparedStatement {

	public PreparedStatement makePstmt(Connection conn) throws SQLException;
	
}
