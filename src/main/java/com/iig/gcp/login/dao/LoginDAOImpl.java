package com.iig.gcp.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.RunFeeds1;
import com.iig.gcp.login.dto.UserAccount;
import com.iig.gcp.login.dto.UserGroup;
import com.iig.gcp.utils.ConnectionUtils;

@Component
@Transactional
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	private ConnectionUtils ConnectionUtils;
	
	private static String USER_MASTER_TABLE = "JUNIPER_USER_MASTER";
	private static String USER_GROUP_MASTER_TABLE = "JUNIPER_USER_GROUP_MASTER";
	private static String UGROUP_USER_MASTER_TABLE = "JUNIPER_UGROUP_USER_LINK";
	private static String FEATURE_MASTER_TABLE = "JUNIPER_FEATURE_MASTER";
	private static String PROJECT_MASTER_TABLE = "JUNIPER_PROJECT_MASTER";

	//User_Group_Constants
	private static String JADMIN_GROUP = "JUNIPER_JADMIN";

	/**
	 * 
	 */
	@Override
	public ArrayList<UserAccount> getUserAccount() throws Exception {
		ArrayList<UserAccount> arrUsers= new ArrayList<UserAccount>();
		String sql = "select user_id,user_sequence from "+USER_MASTER_TABLE+"";
		Connection conn = null;
		try {
			conn= ConnectionUtils.getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql); 
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				UserAccount user = new UserAccount();
				user.setUser_id(rs.getString(1));
				user.setUser_sequence(rs.getInt(2));
				arrUsers.add(user);	
			}
			pstm.close();
			rs.close();
		}catch(Exception e) {
			throw e;
		}finally {
			conn.close();
		}
		return arrUsers;
	}

	@Override
	public UserAccount findUserFromId(String user_id) throws Exception {

		String sql = "select user_id,user_pass,user_sequence from juniper_user_master where user_id='"+user_id+"'";
		Connection conn= null;
		UserAccount user = new UserAccount();
		try {
		 conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql); 
		ResultSet rs = pstm.executeQuery();
		
		while (rs.next()) {
			user.setUser_id(rs.getString(1));
			user.setUser_pass(rs.getString(2));
			user.setUser_sequence(rs.getInt(3));
		}
		pstm.close();
		rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		finally {
			conn.close();
		}
		return user;
	}


	@Override
	public List<String> findUserRoles(String user_id) throws Exception {

		String sql = "select distinct ugm.feature_list from juniper_user_master u,juniper_ugroup_user_link ugl,juniper_user_group_master ugm where u.user_sequence=ugl.user_sequence and ugl.USER_GROUP_SEQUENCE =ugm.USER_GROUP_SEQUENCE and u.USER_ID='"+user_id+"'";
		Connection conn= null;
		List<String> userRole = new ArrayList<String>();
		try {
		 conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql); 
		ResultSet rs = pstm.executeQuery();
		

		Set<String> features = new HashSet<String>();
		while (rs.next()) {
			for(String r: rs.getString(1).split(",")) {
				//featureId.append(r + ",");
				features.add(r);
				//userRole.add(r);	
			}
		}
		StringBuffer featureId = new StringBuffer();
		Iterator<String> it = features.iterator();
		while(it.hasNext()) {
			featureId.append(it.next()+ ",");
		}
		if(featureId.length() > 0) {
			featureId.setLength(featureId.length()-1);
			sql = "select feature_name FROM juniper_feature_master f where f.feature_sequence in (" +featureId.toString()+ ")";
			pstm = conn.prepareStatement(sql); 
			rs = pstm.executeQuery();
			while (rs.next()) {
				userRole.add(rs.getString(1));	
			}
		}
		pstm.close();
		rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			conn.close();
		}
		//ConnectionUtils.closeQuietly(conn);
		
		return userRole;
	}


	/**
	 * 
	 */
	@Override
	public ArrayList<Project> getProjects(String username) throws Exception {

		ArrayList<Project> arrProject=new ArrayList<Project>();
		String sql =  "SELECT  DISTINCT p.PROJECT_ID ,p.project_sequence FROM "+UGROUP_USER_MASTER_TABLE+" l inner join "
				+ "juniper_project_master p on l.project_sequence=p.project_sequence where user_sequence in "
				+ "(select user_sequence from "+USER_MASTER_TABLE+" where user_id=?)order by p.project_sequence desc";
		
		Connection  conn= null;
		try {
		 conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, username);
		ResultSet rs = pstm.executeQuery();
		Project prj =null;
		while (rs.next()) {
			prj=new Project();
			prj.setProject_id(rs.getString(1));
			prj.setProject_sequence(rs.getInt(2));
			arrProject.add(prj);	
		}
		pstm.close();
		rs.close();
		conn.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			conn.close();
		}
		return arrProject;

	}

	/**
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public List<UserGroup> fetchGroupDetailsForUser(Connection conn,int user_sequence,String project) throws ClassNotFoundException, SQLException {
		List<UserGroup> userGroups=new ArrayList<UserGroup>();

		String sql = "select distinct b.user_group_sequence,b.feature_list from \r\n" + 
				"(select user_group_sequence from "+UGROUP_USER_MASTER_TABLE+"  where user_sequence=? and project_sequence in (select project_sequence from "+PROJECT_MASTER_TABLE+ " where project_id='"+project+"'))a\r\n" + 
				"join \r\n" + 
				"(select * from "+USER_GROUP_MASTER_TABLE+")b\r\n" + 
				"on a.user_group_sequence = b.user_group_sequence";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, user_sequence);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			UserGroup ugroup = new UserGroup();
			ugroup.setUser_group_sequence(Integer.parseInt(rs.getString(1)));
			ugroup.setFeature_list(rs.getString(2));
			userGroups.add(ugroup);
		}
		pstm.close();
		rs.close();
		//ConnectionUtils.closeQuietly(conn);
		return userGroups;

	}

	/**
	 * 
	 * @param user_sequence
	 * @param project
	 * @return
	 * @throws ClassNotFoundException, SQLException,Exception 
	 */
	@Override
	public String getMenuCodes(int user_sequence,String project) throws ClassNotFoundException, SQLException,Exception {
		String menu_code="";
		String menu_link=null;
		List<String> menu_name=new ArrayList<String>();
		List<Integer> menu_levell=new ArrayList<Integer>();  
		int menu_level=0;
		int i=0;
		Connection conn= null;
		try {
		conn= ConnectionUtils.getConnection();
		List<UserGroup> userGroups=fetchGroupDetailsForUser(conn, user_sequence,project);
		Set<Integer>sortedFeatureList=fetchSortedFeatureIds(userGroups);
		String sql = "select f.feature_link,f.feature_level,f.feature_name FROM "+FEATURE_MASTER_TABLE+" f where f.feature_sequence=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		for(int featureId:sortedFeatureList) {
			pstm.setInt(1, featureId);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				/*menu_link = rs.getString(1);
				menu_level = rs.getInt(2);
				menu_name.add(rs.getString(3));
				menu_levell.add(menu_level);
				if(menu_level==1){
					if(i==0){
						menu_code=menu_link;
					}
					else{
						menu_code=menu_code+"</ul></div></li>"+menu_link;
					}
				}
				else{
					menu_code=menu_code+menu_link;
				}
				i++;*/
				menu_link = rs.getString(1);
				menu_level = rs.getInt(2);
				menu_name.add(rs.getString(3));
				menu_levell.add(menu_level);

				menu_code=menu_code+menu_link;
			}
		}
		pstm.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			conn.close();
		}
		//ConnectionUtils.closeQuietly(conn);
		return menu_code;
	}


	/**
	 * 
	 * @param userGroup
	 * @return
	 */
	private Set<Integer> fetchSortedFeatureIds(List<UserGroup> userGroups) {
		Set<Integer> sortedFeatures = new TreeSet<Integer>();
		for(UserGroup userGroup:userGroups){
			String feature = userGroup.getFeature_list();
			Set<Integer> features = new HashSet<Integer>();
			if(feature!=null&&!feature.isEmpty()){
				List<String> featureList = Arrays.asList(feature.split(","));
				for(String current:featureList){
					features.add(Integer.parseInt(current));
				}
				sortedFeatures.addAll(features);
			}
		}
		return sortedFeatures;
	}

	/**
	 * This method check if user belongs to JAdmin, if yes he will be given JAdmin features.
	 * @throws ClassNotFoundException, SQLException,Exception 
	 */
	@Override
	public String getJAdminMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException,Exception {
		String menu_code="";
		String menu_link=null;
		List<String> menu_name=new ArrayList<String>();
		List<Integer> menu_levell=new ArrayList<Integer>();  
		int menu_level=0;
		int i=0;
		Connection conn= null;
		try {
		conn= ConnectionUtils.getConnection();
		List<UserGroup> userGroups=fetchJAdminGroupDetailsForUser(conn, user_sequence);
		String isAdmin=checkIfUserBelongsToJAdminGroup(conn,userGroups);
		//If user is not part of JAdmin then return
		if(!isAdmin.equals("Y")) {
			return menu_code;
		}
		Set<Integer>sortedFeatureList=fetchSortedFeatureIds(userGroups);
		String sql = "select f.feature_link,f.feature_level,f.feature_name FROM "+FEATURE_MASTER_TABLE+" f where f.feature_admin= ? and f.feature_sequence=?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		for(int featureId:sortedFeatureList) {
			pstm.setString(1, "Y");
			pstm.setInt(2, featureId);
			ResultSet rs = pstm.executeQuery();
			while(rs.next()){
				/*menu_link = rs.getString(1);
				menu_level = rs.getInt(2);
				menu_name.add(rs.getString(3));
				menu_levell.add(menu_level);
				if(menu_level==1){
					if(i==0){
						menu_code=menu_link;
					}
					else{
						menu_code=menu_code+"</ul></div></li>"+menu_link;
					}
				}
				else{
					menu_code=menu_code+menu_link;
				}
				i++;*/
				menu_link = rs.getString(1);
				menu_level = rs.getInt(2);
				menu_name.add(rs.getString(3));
				menu_levell.add(menu_level);

				menu_code=menu_code+menu_link;
			}
		}
		pstm.close();
		//ConnectionUtils.closeQuietly(conn);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			conn.close();
		}
		return menu_code=menu_code+"</ul></div></li>";
	}

	/**
	 * 
	 * @param conn
	 * @param userGroups
	 * @return
	 * @throws SQLException 
	 */
	private String checkIfUserBelongsToJAdminGroup(Connection conn,List<UserGroup> userGroups) throws SQLException {
		for(UserGroup userGroup:userGroups){
			String sql = "select user_group_name from "+USER_GROUP_MASTER_TABLE+" where user_group_sequence="+userGroup.getUser_group_sequence()+"";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				String userGroupName=rs.getString(1);
				if(userGroupName.equals(""+JADMIN_GROUP+"")) {
					return "Y";
				}
			}
			pstm.close();
			rs.close();
			//conn.close();
		}
		return "N";
	}



	/**
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	public List<UserGroup> fetchJAdminGroupDetailsForUser(Connection conn,int user_sequence) throws ClassNotFoundException, SQLException {
		List<UserGroup> userGroups=new ArrayList<UserGroup>();

		String sql = "select distinct b.user_group_sequence,b.feature_list from \r\n" + 
				"(select user_group_sequence from "+UGROUP_USER_MASTER_TABLE+"  where user_sequence=?)a\r\n" + 
				"join \r\n" + 
				"(select * from "+USER_GROUP_MASTER_TABLE+")b\r\n" + 
				"on a.user_group_sequence = b.user_group_sequence";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, user_sequence);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			UserGroup ugroup = new UserGroup();
			ugroup.setUser_group_sequence(Integer.parseInt(rs.getString(1)));
			ugroup.setFeature_list(rs.getString(2));
			userGroups.add(ugroup);
		}
		pstm.close();
		rs.close();
		//ConnectionUtils.closeQuietly(conn);
		return userGroups;

	}


	@Override
	public ArrayList<RunFeeds1> getProjectFeeds(String project) throws Exception {
		ArrayList<RunFeeds1> feeds_arr = new ArrayList<RunFeeds1>();
		String sql = "SELECT fm.FEED_UNIQUE_NAME,cm.SRC_CONN_TYPE|| ' ' ||fm.EXTRACTION_TYPE FROM (select a.src_conn_sequence,b.extraction_type,b.feed_unique_name,b.project_sequence from JUNIPER_EXT_FEED_SRC_TGT_LINK a,JUNIPER_EXT_FEED_MASTER b where a.feed_sequence = b.feed_sequence) fm LEFT JOIN JUNIPER_EXT_SRC_CONN_MASTER cm on fm.SRC_CONN_SEQUENCE=cm.SRC_CONN_SEQUENCE WHERE fm.PROJECT_SEQUENCE in (SELECT PROJECT_SEQUENCE FROM JUNIPER_PROJECT_MASTER WHERE PROJECT_ID=?)";
		Connection conn= null;
		try {
		conn= ConnectionUtils.getConnection();
		RunFeeds1 rf = null;
		PreparedStatement pstm = conn.prepareStatement(sql); 
		pstm.setString(1, project);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			rf = new RunFeeds1();
			rf.setFeed_name(rs.getString(1));
			rf.setFeed_type(rs.getString(2));
			feeds_arr.add(rf);
		}
		pstm.close();
		rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			conn.close();
		}
		//ConnectionUtils.closeQuietly(conn);
		return feeds_arr;
	}

	@Override
	public ArrayList<RunFeeds1> getProjectUsers(String project) throws SQLException, Exception {
		ArrayList<RunFeeds1> users_arr = new ArrayList<RunFeeds1>();
		RunFeeds1 rf = null;
		String sql = "SELECT DISTINCT ul.USER_SEQUENCE,um.USER_ID,um.USER_FULLNAME FROM JUNIPER_UGROUP_USER_LINK ul LEFT JOIN JUNIPER_USER_MASTER um ON ul.USER_SEQUENCE=um.USER_SEQUENCE WHERE ul.PROJECT_SEQUENCE in (SELECT PROJECT_SEQUENCE FROM JUNIPER_PROJECT_MASTER WHERE PROJECT_ID=?)";
		
		Connection conn= null;
		try {
		conn= ConnectionUtils.getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql); 
		pstm.setString(1, project);
		ResultSet rs = pstm.executeQuery();
		while (rs.next()) {
			rf = new RunFeeds1();
			rf.setUser_seq(rs.getString(1));
			rf.setUser_id(rs.getString(2));
			rf.setUser_name(rs.getString(3));
			users_arr.add(rf);
		}
		pstm.close();
		rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			conn.close();
		}
		//ConnectionUtils.closeQuietly(conn);
		return users_arr;
	}

	@Override
	public ArrayList<RunFeeds1> getCurrentRuns(String project) throws SQLException, Exception {
		ArrayList<RunFeeds1> cruns_arr = new ArrayList<RunFeeds1>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		RunFeeds1 rf = null;
		try {
		connection = ConnectionUtils.getConnection();
		pstm = connection.prepareStatement("SELECT DISTINCT JOB_ID , BATCH_ID ,CONCAT(CONCAT(BATCH_DATE,' '),JOB_SCHEDULE_TIME) AS SCHEDULE_TIME, TO_CHAR(START_TIME, 'DD-MON-YY HH24:MI:SS') AS START_TIME, TO_CHAR(END_TIME, 'DD-MON-YY HH24:MI:SS') AS END_TIME FROM JUNIPER_SCH_CURRENT_JOB_DETAIL WHERE TO_CHAR(START_TIME, 'DD-MON-YY HH24:MI:SS') BETWEEN TO_CHAR(SYSDATE-1, 'DD-MON-YY HH24:MI:SS') AND  TO_CHAR(SYSDATE, 'DD-MON-YY HH24:MI:SS') AND STATUS='R'  AND PROJECT_ID IN (SELECT PROJECT_SEQUENCE FROM JUNIPER_PROJECT_MASTER WHERE PROJECT_ID=?) ORDER BY START_TIME DESC");
		pstm.setString(1, project);
		rs = pstm.executeQuery();
		while (rs.next()) {
			rf = new RunFeeds1();
			rf.setJob_id(rs.getString(1));
			rf.setBatch_id(rs.getString(2));
			rf.setExtract_date(rs.getString(3));
			rf.setStart_time(rs.getString(4));
			rf.setEnd_time(rs.getString(5));
			cruns_arr.add(rf);
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		//ConnectionUtils.closeQuietly(connection);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			connection.close();
		}
		return cruns_arr;
	}

	@Override
	public ArrayList<RunFeeds1> getLastRuns(String project) throws SQLException, Exception {
		ArrayList<RunFeeds1> lruns_arr = new ArrayList<RunFeeds1>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		RunFeeds1 rf = null;
		try {
		connection = ConnectionUtils.getConnection();
		pstm = connection.prepareStatement("SELECT DISTINCT JOB_ID , BATCH_ID ,CONCAT(CONCAT(BATCH_DATE,' '),JOB_SCHEDULE_TIME) AS SCHEDULE_TIME, TO_CHAR(START_TIME, 'DD-MON-YY HH24:MI:SS') AS START_TIME, TO_CHAR(END_TIME, 'DD-MON-YY HH24:MI:SS') AS END_TIME FROM JUNIPER_SCH_CURRENT_JOB_DETAIL WHERE TO_CHAR(START_TIME, 'DD-MON-YY HH24:MI:SS') BETWEEN TO_CHAR(SYSDATE-1, 'DD-MON-YY HH24:MI:SS') AND  TO_CHAR(SYSDATE, 'DD-MON-YY HH24:MI:SS') AND STATUS='C'  AND PROJECT_ID IN (SELECT PROJECT_SEQUENCE FROM JUNIPER_PROJECT_MASTER WHERE PROJECT_ID=?) ORDER BY START_TIME DESC");
		pstm.setString(1, project);
		rs = pstm.executeQuery();
		while (rs.next()) {
			rf = new RunFeeds1();
			rf.setJob_id(rs.getString(1));
			rf.setBatch_id(rs.getString(2));
			rf.setExtract_date(rs.getString(3));
			rf.setStart_time(rs.getString(4));
			rf.setEnd_time(rs.getString(5));
			lruns_arr.add(rf);
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		//ConnectionUtils.closeQuietly(connection);
		}
		catch(Exception e ) {
			e.printStackTrace();
			throw e;
		}
		finally {
			connection.close();
		}
		return lruns_arr;
	}

	@Override
	public ArrayList<RunFeeds1> getUpcomingRuns(String project) throws SQLException, Exception {
		ArrayList<RunFeeds1> uruns_arr = new ArrayList<RunFeeds1>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		RunFeeds1 rf = null;
		try {
		connection = ConnectionUtils.getConnection();
		pstm = connection.prepareStatement("SELECT DISTINCT JOB_ID , BATCH_ID ,CONCAT(CONCAT(BATCH_DATE,' '),JOB_SCHEDULE_TIME) AS SCHEDULE_TIME, TO_CHAR(START_TIME, 'DD-MON-YY HH24:MI:SS') AS START_TIME, TO_CHAR(END_TIME, 'DD-MON-YY HH24:MI:SS') AS END_TIME FROM JUNIPER_SCH_CURRENT_JOB_DETAIL WHERE TO_CHAR(START_TIME, 'DD-MON-YY HH24:MI:SS') BETWEEN TO_CHAR(SYSDATE, 'DD-MON-YY HH24:MI:SS') AND  TO_CHAR(SYSDATE+1, 'DD-MON-YY HH24:MI:SS')  AND PROJECT_ID IN (SELECT PROJECT_SEQUENCE FROM JUNIPER_PROJECT_MASTER WHERE PROJECT_ID=?) ORDER BY START_TIME DESC");
		pstm.setString(1, project);
		rs = pstm.executeQuery();
		while (rs.next()) {
			rf = new RunFeeds1();
			rf.setJob_id(rs.getString(1));
			rf.setBatch_id(rs.getString(2));
			rf.setExtract_date(rs.getString(3));
			rf.setStart_time(rs.getString(4));
			rf.setEnd_time(rs.getString(5));
			uruns_arr.add(rf);
		}	
		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		//ConnectionUtils.closeQuietly(connection);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			connection.close();
		}
		return uruns_arr;
	}

	@Override
	public ArrayList<RunFeeds1> getFailedRuns(String project) throws SQLException, Exception {
		ArrayList<RunFeeds1> fruns_arr = new ArrayList<RunFeeds1>();
		Connection connection = null;
		ResultSet rs =null;
		PreparedStatement pstm =null;
		RunFeeds1 rf = null;
		try {
		connection = ConnectionUtils.getConnection();
		pstm = connection.prepareStatement("SELECT DISTINCT FEED_UNIQUE_NAME,EXTRACTED_DATE,PG_TYPE,RUN_ID,JOB_START_TIME FROM JUNIPER_EXT_NIFI_STATUS WHERE upper(trim(STATUS)) = 'FAILED' and TO_CHAR(JOB_START_TIME, 'DD-MON-YY HH24:MI:SS') BETWEEN TO_CHAR(SYSDATE-1, 'DD-MON-YY HH24:MI:SS') AND  TO_CHAR(SYSDATE, 'DD-MON-YY HH24:MI:SS')  and  ROWNUM  <=10 and PROJECT_SEQUENCE=(SELECT PROJECT_SEQUENCE FROM JUNIPER_PROJECT_MASTER WHERE PROJECT_ID=?) ORDER BY JOB_START_TIME DESC");
		pstm.setString(1, project);
		rs = pstm.executeQuery();
		while (rs.next()) {
			rf = new RunFeeds1();
			rf.setFeed_name(rs.getString(1));
			rf.setExtract_date(rs.getString(2));
			rf.setFeed_type(rs.getString(3));
			rf.setRun_id(rs.getString(4));
			rf.setStart_time(rs.getString(5));
			fruns_arr.add(rf);
		}	


		ConnectionUtils.closeResultSet(rs);
		ConnectionUtils.closePrepareStatement(pstm);
		//ConnectionUtils.closeQuietly(connection);
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			connection.close();
		}
		return fruns_arr;
	}
}
