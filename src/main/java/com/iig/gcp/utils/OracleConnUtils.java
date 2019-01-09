package com.iig.gcp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.iig.gcp.constants.OracleConstants;

@Component
public class OracleConnUtils {

	
	private static String oracle_ip_port;
	@Value("${oracle.ip.port.sid}")
	public void setSid(String value) {
		this.oracle_ip_port=value;
	}
	
	
	private static String oracle_jdbc_url;
	@Value("${oracle.jdbc.url}")
	public void setJdbcUrl(String value) {
		this.oracle_jdbc_url=value;
	}
	
	
	private static String oracle_user_name;
	@Value("${oracle.user.name}")
	public void setDBName(String value) {
		this.oracle_user_name=value;
	}
	
	
	private static String oracle_pwd;
	@Value("${oracle.password}")
	public void setPassword(String value) {
		this.oracle_pwd=value;
	}
	
	public static Connection getOracleConnection() throws ClassNotFoundException, SQLException {
		Class.forName(OracleConstants.ORACLE_DRIVER);
		String connectionUrl = oracle_jdbc_url.replaceAll("#orcl_ip", oracle_ip_port);
		Connection conn = DriverManager.getConnection(connectionUrl, oracle_user_name,
				oracle_pwd);
		return conn;
	}

}
