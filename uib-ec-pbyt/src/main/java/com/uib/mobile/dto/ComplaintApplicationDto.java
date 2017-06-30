package com.uib.mobile.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ComplaintApplicationDto {
	private String id;
	private String memberId;   //会员Id
	private String feedbackType;   //反馈类型
	private String describeInfo;  //详情描述
	private String images;  //反馈图片
	private List<TbAttachment> tbAttachment;
	private UserComplaintAttrMap userComplaintAttrMap;
	private Date createTime;
	private String title;
	private Date solutionTime;
	private Date modifyDate;
	private String delFlag;
	private String solutionState;
	private String reply;
	private String createTimeStr;
	private String solutionTimeStr;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	
	public String getDescribeInfo() {
		return describeInfo;
	}
	public void setDescribeInfo(String describeInfo) {
		this.describeInfo = describeInfo;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr2(){
		if(createTime == null){
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
	}
	
	public String getSolutionTimeStr2(){
		if(solutionTime == null){
			return "";
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(solutionTime);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSolutionTime() {
		return solutionTime;
	}
	public void setSolutionTime(Date solutionTime) {
		this.solutionTime = solutionTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getSolutionState() {
		return solutionState;
	}
	public void setSolutionState(String solutionState) {
		this.solutionState = solutionState;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getSolutionTimeStr() {
		return solutionTimeStr;
	}
	public void setSolutionTimeStr(String solutionTimeStr) {
		this.solutionTimeStr = solutionTimeStr;
	}
	public List<TbAttachment> getTbAttachment() {
		return tbAttachment;
	}
	public void setTbAttachment(List<TbAttachment> tbAttachment) {
		this.tbAttachment = tbAttachment;
	}
	public UserComplaintAttrMap getUserComplaintAttrMap() {
		return userComplaintAttrMap;
	}
	public void setUserComplaintAttrMap(UserComplaintAttrMap userComplaintAttrMap) {
		this.userComplaintAttrMap = userComplaintAttrMap;
	}
	
}
