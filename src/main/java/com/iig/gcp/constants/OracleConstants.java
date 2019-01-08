package com.iig.gcp.constants;

public class OracleConstants {
	
	// Oracle Driver Details 
	public final static String ORACLE_DRIVER="oracle.jdbc.driver.OracleDriver";
	public final static String ORACLE_IP_PORT_SID="//35.237.85.235:1521/pdborcl.us_east1_b.c.juniperonprem.internal";
	public static final String HIVE_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	public final static String ORACLE_DB_NAME="micro";
	public final static String ORACLE_JDBC_URL="jdbc:oracle:thin:@"+ORACLE_IP_PORT_SID+"";
	public static final String ORACLE_PASSWORD = "services";
	public static final String masterKeyLocation = "master_key.txt";
	
	public final static String INSERTQUERY = "insert into {$table}({$columns}) values({$data})";
	public final static String QUOTE = "'";
	public final static String COMMA = ",";
	public final static String SCHEDULETABLE = "JUNIPER_SCH_MASTER_JOB_DETAIL";
	public final static String EXTRACTION_COMPUTE_URL1="http://35.231.171.195:5125/";
}
