package com.uib.mobile.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.mobile.dao.ComplainApplicationDao;
import com.uib.mobile.dao.UserComplaintAttrMapDao;
import com.uib.mobile.dto.ComplaintApplicationDto;
import com.uib.mobile.dto.UserComplaintAttrMap;
@Component
public class UserComplaintAttrMapDaoImpl extends MyBatisDao<UserComplaintAttrMap> implements UserComplaintAttrMapDao{

	public void save(UserComplaintAttrMap userComplaintAttrMap) throws Exception {
		this.save("save",userComplaintAttrMap);
	}

}
