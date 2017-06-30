package com.uib.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.mobile.dao.UserComplaintAttrMapDao;
import com.uib.mobile.dto.UserComplaintAttrMap;
import com.uib.mobile.service.UserComplaintAttrMapService;
@Service
public class UserComplaintAttrMapServiceImpl implements UserComplaintAttrMapService{

	@Autowired
	private UserComplaintAttrMapDao userComplaintAttrMapdao;
	@Override
	public void save(UserComplaintAttrMap userComplaintAttrMap) throws Exception {
		userComplaintAttrMapdao.save(userComplaintAttrMap);
	}

}
