package com.uib.core.web;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class TreeUtils implements Serializable{
	
	private String id;
	
	private String text;
	
	private String checked = "false";
	
//	private TreeAttributes  attributes = new TreeAttributes();
	
	private List<TreeUtils> children;

	private String state;
	
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public List<TreeUtils> getChildren() {
		return children;
	}

	public void setChildren(List<TreeUtils> children) {
		this.children = children;
	}
	
}
