package com.iig.gcp.admin.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.admin.dao.AdminDAO;
import com.iig.gcp.admin.dto.Feature;
import com.iig.gcp.admin.dto.Group;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.UserAccount;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO admindao;
	
	@Override
	public String getUser(String user) throws Exception {
		// TODO Auto-generated method stub
		return admindao.getUser(user);
	}
	
	@Override
	public ArrayList<String> getAllUsers() throws Exception {
		// TODO Auto-generated method stub
		return admindao.getAllUsers();
	}

	@Override
	public String  onBoardUser(@Valid String x, @Valid boolean isAdmin, HttpServletRequest request) throws Exception {
		JSONObject jsonObject=null;
		try {
		jsonObject= new JSONObject(x);
		}catch(JSONException e) {
			throw new Exception("Please Select User ID for Onboarding");
		}
		String group_seq=jsonObject.getString("group");
		String username=jsonObject.getString("username");
		String project=jsonObject.getString("projects");
		 return admindao.adminupdusrgrp(username,group_seq,project);		
		
	}
	
	@Override
	public String registerProject(@Valid String projectId, String projectName, String projectOwner,
			String projectDetails, int userSequence) throws ClassNotFoundException, Exception {
		return admindao.registerProject(projectId,projectName,projectOwner,projectDetails,userSequence);
	}

	@Override
	public Project getProject(@Valid String projectId) throws Exception{
		// TODO Auto-generated method stub
		return admindao.getProject(projectId);
	}

	@Override
	public String registerAddAdminAccess(int projectSeq, int user_sequence) throws Exception {
		// TODO Auto-generated method stub
		return admindao.registerAddAdminAccess(projectSeq,user_sequence);
	}

	@Override
	public ArrayList<Feature> getFeatures(String userid,String project) throws Exception {
		
		return admindao.getFeatures(userid,project);
	}

	@Override
	public ArrayList<Feature> getFeaturesAlready(String userid,String project) throws Exception {
		
		return admindao.getFeaturesAlready(userid,project);
	}

	@Override
	public int getUserSequence(String userid) throws Exception {
		
		return admindao.getUserSequence(userid);
	}
	
	@Override
	public ArrayList<Group> getGroups(String user, String project) throws Exception {
		
		return admindao.getGroups(user,project);
	}
	
	@Override
	public ArrayList<Group> getGroupsAlready(String user, String project) throws Exception {
		
		return admindao.getGroupsAlready(user,project);
	}
	
	
	@Override
	public List<String> getProjects() {
		return admindao.getProjects();
	}

	@Override
	public Project fetchProjectDetails(@Valid String projectId) {
		return admindao.fetchProjectDetails(projectId);
	}

	@Override
	public String updateProject(@Valid String projectId, String projectName, String projectOwner, String projectDescription,
			String user_id) {
		return admindao.updateProject(projectId,projectName,projectOwner,projectDescription);
	}

/*
 // Commenting this code as of now, can be used if needed in future
	@Override
	public String deleteProject(@Valid String projectId) throws Exception {
		return admindao.deleteProject(projectId);
		}*/

	@Override
	public String registerUserInSystem(@Valid String userId,  String domain,String onboardedUserFullName, String userEmail,
			String loggedInUser) throws ClassNotFoundException, SQLException {
		return admindao.registerUser(userId,domain,onboardedUserFullName,userEmail,loggedInUser);	
	}

	@Override
	public String updateUser(@Valid String userId, String domain, String onboardedUserFullName,String userEmail, String loggedInUser) throws ClassNotFoundException, SQLException {
		return admindao.updateUser(userId,domain,onboardedUserFullName,userEmail,loggedInUser);	
	}

	@Override
	public List<String> fetchUserIds() throws ClassNotFoundException, SQLException {
		return admindao.fetchUserIds();
	}

	@Override
	public UserAccount fetchUserDetails(String userId) throws ClassNotFoundException, SQLException {
		return admindao.fetchUserAttributes(userId);	
	}

	@Override
	public String linkUserGroupsToNewProject(int userSequence,Project project) throws ClassNotFoundException, SQLException {
		return admindao.linkUserGroupsToProject(userSequence,project);
	}

	@Override
	public int checkPSID(@Valid String psid) {
		return admindao.validatePSID(psid);
	}

}
