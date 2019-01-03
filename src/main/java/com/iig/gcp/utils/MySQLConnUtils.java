package com.iig.gcp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.iig.gcp.constants.MySqlConstants;

public class MySQLConnUtils {
	

	public static Connection getMySQLConnection()
	         throws ClassNotFoundException, SQLException {
		 
	    Class.forName(MySqlConstants.MYSQL_DRIVER);
	     Connection conn = DriverManager.getConnection(MySqlConstants.MYSQL_JDBC_URL, MySqlConstants.MYSQL_USER,
	    		 MySqlConstants.MYSQL_PASSWORD);
	     return conn;
	 }
	
		
}
