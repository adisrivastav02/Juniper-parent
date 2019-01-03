package com.iig.gcp.constants;

public class MySqlConstants {
	
	// MySQL Driver Details 
	public final static String MYSQL_DRIVER="com.mysql.jdbc.Driver";
	public final static String MYSQL_IP_PORT="35.237.17.136:3306";
	public final static String MYSQL_DB_NAME="iigs_scheduler_db";
	public final static String MYSQL_JDBC_URL="jdbc:mysql://"+MYSQL_IP_PORT+"/"+MYSQL_DB_NAME;
	public final static String MYSQL_USER="onprime";
	public static final String MYSQL_PASSWORD = "Infy@123";
	
	
	public final static String EXTRACTION_COMPUTE_URL="http://35.237.17.136:8095/";
	public static String GET_SOURCE_CONNECTIONS="SELECT connection_id,connection_name,host_name,port_no,username,password,database_name,service_name from connection_master where connection_type=?";


	public final static String PROJECTNAME="juniperonprem";
	
	public final static String PUBLISHING_COMPUTE_URL="http://35.190.191.154:8095/";
	public final static String PUBLISHING_PYTHON_REST_URL="http://35.185.47.113:5010/";
	
	
}
