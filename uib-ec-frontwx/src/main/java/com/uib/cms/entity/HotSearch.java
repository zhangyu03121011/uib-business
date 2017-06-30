/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.cms.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

/**
 * 热搜词管理Entity
 * @author zfan
 * @version 2015-11-12
 */
public class HotSearch {
	
	private String id;
	private String keyword;		// keyword
	private String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	private Date createTime;	//创建时间
	
	public HotSearch() {
		super();
	}


	@Length(min=0, max=30, message="keyword长度必须介于 0 和 30 之间")
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}