/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 会员投诉Entity
 * @author luogc
 * @version 2016-01-12
 */
public class UserComplaint extends DataEntity<UserComplaint> {
	
	private static final long serialVersionUID = 1L;
	private String memberId;		// 会员ID
	private String feedbackType;		// 投诉类型（1：商品，2：服务，3：其它）
	private String title;		// 标题
	private String describeinfo;		// 详情描述
	private String images;		// 投诉图片
	private Date createTime;		// 创建时间
	private Date solutionTime;		// 解决时间
	private Date modifyDate;		// modify_date
	private String solutionState;		// 解决状态（为空表示待解决，1：表示已解决）
	private String reply;		// 答复
	private String username;		// 用户名
	private List<UserComplaintAttachment> userComplaintAttachmentList;	//文件表关联
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserComplaint() {
		super();
	}

	public UserComplaint(String id){
		super(id);
	}

	@Length(min=0, max=64, message="会员ID长度必须介于 0 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=255, message="投诉类型（1：商品，2：服务，3：其它）长度必须介于 0 和 255 之间")
	public String getFeedbackType() {
		return feedbackType;
	}

	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	
	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="详情描述长度必须介于 0 和 255 之间")
	public String getDescribeinfo() {
		return describeinfo;
	}

	public void setDescribeinfo(String describeinfo) {
		this.describeinfo = describeinfo;
	}
	
	@Length(min=0, max=255, message="投诉图片长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=1, message="解决状态（为空表示待解决，1：表示已解决）长度必须介于 0 和 1 之间")
	public String getSolutionState() {
		return solutionState;
	}

	public void setSolutionState(String solutionState) {
		this.solutionState = solutionState;
	}
	
	@Length(min=0, max=255, message="答复长度必须介于 0 和 255 之间")
	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public List<UserComplaintAttachment> getUserComplaintAttachmentList() {
		return userComplaintAttachmentList;
	}

	public void setUserComplaintAttachmentList(
			List<UserComplaintAttachment> userComplaintAttachmentList) {
		this.userComplaintAttachmentList = userComplaintAttachmentList;
	}
	
}