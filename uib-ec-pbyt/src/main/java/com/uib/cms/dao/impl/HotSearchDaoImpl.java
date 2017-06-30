package com.uib.cms.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.uib.cms.dao.HotSearchDao;
import com.uib.cms.entity.HotSearch;
import com.uib.core.dao.MyBatisDao;

@Component
public class HotSearchDaoImpl extends MyBatisDao<HotSearch> implements HotSearchDao{

	@Override
	public List<HotSearch> getHotSearchList(@Param("count") Integer count) {
		return this.get("getHotSearchList", count);
	}

	@Override
	public HotSearch getById(String id) {
		return this.getUnique("getById", id);
	}

}
