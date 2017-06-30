/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.cms.entity.HotSearch;


/**
 * 热搜词管理DAO接口
 * @author zfan
 * @version 2015-11-12
 */
public interface HotSearchDao {
	
	/***
	 * 根据传入的条数查询
	 * @param count
	 * @return
	 */
	public List<HotSearch> getHotSearchList(@Param("count") Integer count);
	
	public HotSearch getById(String id);
	
}