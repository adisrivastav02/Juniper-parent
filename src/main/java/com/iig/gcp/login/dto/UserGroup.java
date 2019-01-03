package com.iig.gcp.login.dto;

public class UserGroup {
	private int user_group_sequence;
	private String user_group_name;
	private String feature_list;
	private int project_sequence;
	public int getUser_group_sequence() {
		return user_group_sequence;
	}
	public void setUser_group_sequence(int user_group_sequence) {
		this.user_group_sequence = user_group_sequence;
	}
	public String getUser_group_name() {
		return user_group_name;
	}
	public void setUser_group_name(String user_group_name) {
		this.user_group_name = user_group_name;
	}
	public String getFeature_list() {
		return feature_list;
	}
	public void setFeature_list(String feature_list) {
		this.feature_list = feature_list;
	}
	public int getProject_sequence() {
		return project_sequence;
	}
	public void setProject_sequence(int project_sequence) {
		this.project_sequence = project_sequence;
	}
	
}
