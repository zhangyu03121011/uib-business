package com.uib.mobile.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dao.CommissionDao;
import com.uib.mobile.dto.Commission;

@Component
public class CommissionDaoImpl extends MyBatisDao<Commission> implements CommissionDao{
	
	@Override
	public List<Commission> findByName(Map<String, Object> params) throws Exception{
		
		return this.getObjectList("findByName", params);
	}

	@Override
	public void deleteCommissionById(String[] ids) throws Exception {
		this.remove("deleteCommissionById",ids);
	}

}
