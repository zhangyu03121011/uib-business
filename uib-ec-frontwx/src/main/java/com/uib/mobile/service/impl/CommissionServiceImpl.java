package com.uib.mobile.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.mobile.dao.CommissionDao;
import com.uib.mobile.dto.Commission;
import com.uib.mobile.service.CommissionService;

@Component
public class CommissionServiceImpl implements CommissionService {
	
	@Autowired
	private CommissionDao commissionDao;

	public List<Commission> findByName(Map<String, Object> params) throws Exception{
		return commissionDao.findByName(params);
	}

	@Override
	public void deleteCommissionById(String[] ids) throws Exception {
		 commissionDao.deleteCommissionById(ids);
	}
}
