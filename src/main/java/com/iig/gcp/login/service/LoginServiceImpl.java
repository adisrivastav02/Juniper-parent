package com.iig.gcp.login.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iig.gcp.login.dao.LoginDAO;
import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.RunFeeds1;
import com.iig.gcp.login.dto.UserAccount;

@Service
public class LoginServiceImpl implements LoginService{

	
	@Autowired
	LoginDAO loginDAO;
	
	@Override
	public ArrayList<UserAccount> getUserAccount() throws Exception {
		return loginDAO.getUserAccount();
	}

	@Override
	public UserAccount findUserFromId(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.findUserFromId(user_id);
	}
	
	@Override
	public List<String> findUserRoles(String user_id) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.findUserRoles(user_id);
	}
	
	@Override
	public ArrayList<Project> getProjects(String username) throws Exception {
		return loginDAO.getProjects(username);
	}

	@Override
	public String getMenuCodes(int user_sequence,String project) throws ClassNotFoundException, SQLException, Exception {
		return loginDAO.getMenuCodes(user_sequence,project);
	}

	@Override
	public String getJAdminMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException, Exception {
		return loginDAO.getJAdminMenuCodes(user_sequence);
	}
	

	@Override
	public ArrayList<RunFeeds1> getProjectFeeds(String project) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getProjectFeeds(project);
	}

	@Override
	public ArrayList<RunFeeds1> getProjectUsers(String project) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getProjectUsers(project);
	}

	@Override
	public ArrayList<RunFeeds1> getLastRuns(String project) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getLastRuns(project);
	}

	@Override
	public ArrayList<RunFeeds1> getUpcomingRuns(String project) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getUpcomingRuns(project);
	}

	@Override
	public ArrayList<RunFeeds1> getFailedRuns(String project) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getFailedRuns(project);
	}

	@Override
	public ArrayList<RunFeeds1> getCurrentRuns(String project) throws Exception {
		// TODO Auto-generated method stub
		return loginDAO.getCurrentRuns(project);
	}

}
