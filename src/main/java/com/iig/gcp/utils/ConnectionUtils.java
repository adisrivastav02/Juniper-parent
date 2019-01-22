package com.iig.gcp.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ConnectionUtils {

	@Autowired
    private DataSource dataSource;
	
	public Connection getConnection() 
			throws ClassNotFoundException, SQLException,Exception {
		System.out.println(dataSource.getClass().getName());
		//return OracleConnUtils.getOracleConnection();
		return dataSource.getConnection();
	}



	public void closeQuietly(Connection conn) {
		try {
			conn.commit();
			conn.close();
		} catch (Exception e) {
		}
	}

	public static void rollbackQuietly(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
		}
	}

	public void closeResultSet(ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
		}
	}

	public void closePrepareStatement(PreparedStatement ps) {
		try {
			ps.close();
		} catch (Exception e) {
		}
	}
}
