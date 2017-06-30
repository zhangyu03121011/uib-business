package com.uib.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.member.dao.AreaDao;
import com.uib.member.entity.Area;

@Component
public class AreaDaoImpl extends MyBatisDao<Area> implements AreaDao{

	@Override
	public List<Area> findAreasByParentId(String parentId) {
		return this.get("findAreasByParentId", parentId);
	}


	

}
