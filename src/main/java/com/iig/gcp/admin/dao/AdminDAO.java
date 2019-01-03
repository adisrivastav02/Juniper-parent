package com.iig.gcp.admin.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.admin.dto.Group;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

public interface AdminDAO {
	String getUser(String user) throws Exception;
	ArrayList<String> getAllUsers() throws Exception;
	String adminupdusrgrp(String username,String group_seq,String project) throws Exception;

	ArrayList<Feature> getFeatures(String userid, String project) throws Exception;

	ArrayList<Feature> getFeaturesAlready(String userid, String project)throws Exception;

	ArrayList<Group> getGroups(String userid, String project) throws Exception;

	ArrayList<Group> getGroupsAlready(String userid, String project)throws Exception;

	
	int getUserSequence(String userid)throws Exception;


	public String registerProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription, int userSequence) throws ClassNotFoundException, Exception;
	Project getProject(@Valid String projectId) throws Exception;
	String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception;
	
	//Project
	List<String> getProjects();
	Project fetchProjectDetails(@Valid String projectId);
	String updateProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription);
	//String deleteProject(@Valid String projectId)throws Exception;
	String getProAdminFeatures()throws Exception;
	void deleteEntries(int projectseq, int selectUser_Seq) throws Exception;

	String registerUser(@Valid String userId,  String domain,String onboardedUserFullName, String userEmail, String loggedInUser) throws ClassNotFoundException, SQLException;

	String updateUser(@Valid String userId, String domain, String onboardedUserFullName,String userEmail, String loggedInUser)throws ClassNotFoundException, SQLException;

	List<String> fetchUserIds() throws ClassNotFoundException, SQLException;

	UserAccount fetchUserAttributes(String userId) throws ClassNotFoundException, SQLException;
	String linkUserGroupsToProject(int userSequence, Project project) throws ClassNotFoundException, SQLException;
	int validatePSID(@Valid String psid);

	
}
