package com.uib.cms.service;

import java.util.List;

import com.uib.cms.entity.HotSearch;

public interface HotSearchService {
	
	public List<HotSearch> getHotSearchList(Integer count);
	
	public HotSearch getById(String id);

}
