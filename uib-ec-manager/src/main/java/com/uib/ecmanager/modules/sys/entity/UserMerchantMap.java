/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.sys.entity;

import com.uib.ecmanager.common.persistence.DataEntity;
/**
 * 用户商户关联Entity
 * @author limy
 * @version 2015-08-25
 */
public class UserMerchantMap extends DataEntity<UserMerchantMap> {
	
	private static final long serialVersionUID = 1L;
	private String merCode;		// 商户Id
	private User user;		// 用户Id
	
	public UserMerchantMap() {
		super();
	}

	public UserMerchantMap(String id){
		super(id);
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMerCode() {
		return merCode;
	}

	public void setMerCode(String merCode) {
		this.merCode = merCode;
	}
	
}