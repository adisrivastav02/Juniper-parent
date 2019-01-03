package com.iig.gcp.admin.dto;

public class Feature {

	private String feature_name ;
	private int feature_order;
	private int feature_level;
	private int feature_sequence;
	private int selected_user_sequence;
	

	public int getSelected_user_sequence() {
		return selected_user_sequence;
	}
	public void setSelected_user_sequence(int selected_user_sequence) {
		this.selected_user_sequence = selected_user_sequence;
	}
	public String getFeature_name() {
		return feature_name;
	}
	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}
	public int getFeature_order() {
		return feature_order;
	}
	public void setFeature_order(int feature_order) {
		this.feature_order = feature_order;
	}
	public int getFeature_level() {
		return feature_level;
	}
	public void setFeature_level(int feature_level) {
		this.feature_level = feature_level;
	}
	public int getFeature_sequence() {
		return feature_sequence;
	}
	public void setFeature_sequence(int feature_sequence) {
		this.feature_sequence = feature_sequence;
	}
	
	
	
}
