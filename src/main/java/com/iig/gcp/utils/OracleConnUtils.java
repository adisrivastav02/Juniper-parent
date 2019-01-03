package com.iig.gcp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.iig.gcp.constants.OracleConstants;

public class OracleConnUtils {

	public static Connection getOracleConnection() throws ClassNotFoundException, SQLException {
		Class.forName(OracleConstants.ORACLE_DRIVER);
		Connection conn = DriverManager.getConnection(OracleConstants.ORACLE_JDBC_URL, OracleConstants.ORACLE_DB_NAME,
				OracleConstants.ORACLE_PASSWORD);
		return conn;
	}

}
