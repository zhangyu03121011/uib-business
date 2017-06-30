package com.uib.mobile.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dao.UserComplaintAttrMapDao;
import com.uib.mobile.dto.UserComplaintAttrMap;
@Component
public class UserComplaintAttrMapDaoImpl extends MyBatisDao<UserComplaintAttrMap> implements UserComplaintAttrMapDao{

	public void save(UserComplaintAttrMap userComplaintAttrMap) throws Exception {
		this.save("save",userComplaintAttrMap);
	}

}
