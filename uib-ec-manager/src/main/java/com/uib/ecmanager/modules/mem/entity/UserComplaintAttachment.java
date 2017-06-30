/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 会员投诉图片表Entity
 * @author luogc
 * @version 2016-01-15
 */
public class UserComplaintAttachment extends DataEntity<UserComplaintAttachment> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 文件名称
	private String filePath;		// 文件路径
	
	public UserComplaintAttachment() {
		super();
	}

	public UserComplaintAttachment(String id){
		super(id);
	}

	@Length(min=1, max=64, message="文件名称长度必须介于 1 和 64 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=1, max=255, message="文件路径长度必须介于 1 和 255 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
}