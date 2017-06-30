package com.uib.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.cms.dao.HotSearchDao;
import com.uib.cms.entity.HotSearch;
import com.uib.cms.service.HotSearchService;

@Component
public class HotSearchServiceImpl implements HotSearchService {

	@Autowired
	private HotSearchDao hotSearchDao;
	
	@Override
	public List<HotSearch> getHotSearchList(Integer count) {
		return hotSearchDao.getHotSearchList(count);
	}

	@Override
	public HotSearch getById(String id) {
		// TODO Auto-generated method stub
		return hotSearchDao.getById("1");
	}

}
