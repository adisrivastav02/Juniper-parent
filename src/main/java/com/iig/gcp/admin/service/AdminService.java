package com.iig.gcp.admin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.admin.dto.Group;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

public interface AdminService {

	String getUser(String user) throws Exception;
	
	ArrayList<String> getAllUsers() throws Exception;
	
	ArrayList<Group> getGroups(String user, String project) throws Exception;
	ArrayList<Group> getGroupsAlready(String user, String project) throws Exception;



String onBoardUser(@Valid String x, @Valid boolean isAdmin, HttpServletRequest request)throws Exception;
	String registerUserInSystem(@Valid String userId,  String domain, String onboardedUserFullName,String userEmail, String loggedInUser) throws ClassNotFoundException, SQLException;

	ArrayList<Feature> getFeatures(String userid, String project)throws Exception;

	ArrayList<Feature> getFeaturesAlready(String userid, String project)throws Exception;

	int getUserSequence(String userid)throws Exception;
	


	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription, int userSequence) throws ClassNotFoundException, Exception;
	Project getProject(@Valid String projectId) throws Exception;

	String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception;
	

	//Project
	List<String> getProjects();
	Project fetchProjectDetails(@Valid String projectId);
	String updateProject(@Valid String projectId, String projectName, String projectOwner, String projectDetails,String user_id);
//String deleteProject(@Valid String projectId)throws Exception;

	String updateUser(@Valid String userId, String userPwd, String onboardedUserFullName,String userEmail, String loggedInUser) throws ClassNotFoundException, SQLException;

	List<String> fetchUserIds() throws ClassNotFoundException, SQLException;

	UserAccount fetchUserDetails(String user_val) throws ClassNotFoundException, SQLException;
	String linkUserGroupsToNewProject(int user_sequence, Project project) throws ClassNotFoundException, SQLException;

	int checkPSID(@Valid String psid);

}
