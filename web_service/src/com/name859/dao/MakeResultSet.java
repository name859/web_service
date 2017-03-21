package com.name859.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface MakeResultSet<T> {

	public T makeRs(ResultSet rs) throws SQLException;
	
}
