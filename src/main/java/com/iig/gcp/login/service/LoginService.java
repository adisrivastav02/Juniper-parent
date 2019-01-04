package com.iig.gcp.login.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iig.gcp.login.dto.Project;
import com.iig.gcp.login.dto.RunFeeds1;
import com.iig.gcp.login.dto.UserAccount;

public interface LoginService {

//User
ArrayList<UserAccount> getUserAccount() throws Exception;

UserAccount findUserFromId(String user_id) throws Exception;

List<String> findUserRoles(String user_id) throws Exception;

//Project
ArrayList<Project> getProjects(String username)throws Exception;

//Feature
String getMenuCodes(int user_sequence, String project) throws ClassNotFoundException, SQLException;
String getJAdminMenuCodes(int user_sequence) throws ClassNotFoundException, SQLException;

//Project Dashboard
ArrayList<RunFeeds1> getProjectFeeds(String project) throws Exception;
ArrayList<RunFeeds1> getProjectUsers(String project) throws Exception;
ArrayList<RunFeeds1> getLastRuns(String project) throws Exception;
ArrayList<RunFeeds1> getUpcomingRuns(String project) throws Exception;
ArrayList<RunFeeds1> getFailedRuns(String project) throws Exception;
ArrayList<RunFeeds1> getCurrentRuns(String project) throws Exception;
}