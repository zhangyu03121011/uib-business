package com.uib.common.bean;

import java.util.Map;

public class MailBean {
	private String[] toEmails;  
	
	private String subject;  
	
	private Map<String, String> data ;          //邮件数据  
	private String template;    //邮件模板 
	public String[] getToEmails() {
		return toEmails;
	}
	public void setToEmails(String[] toEmails) {
		this.toEmails = toEmails;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
	

}
