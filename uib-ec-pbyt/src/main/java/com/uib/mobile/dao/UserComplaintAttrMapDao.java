package com.uib.mobile.dao;

import com.uib.mobile.dto.UserComplaintAttrMap;

/**
 * 投诉申请&文件的关联表
 * @author admia
 *
 */
public interface UserComplaintAttrMapDao {
	/**
	 * 添加
	 * @param complaintId
	 * @param attachmentId
	 * @throws Exception
	 */
	public void save(UserComplaintAttrMap userComplaintAttrMap)throws Exception;
}
