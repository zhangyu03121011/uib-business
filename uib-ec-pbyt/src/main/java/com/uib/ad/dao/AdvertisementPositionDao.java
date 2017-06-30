package com.uib.ad.dao;

import com.uib.ad.entity.AdvertisementPosition;

public interface AdvertisementPositionDao {
	
	/**
	 * 根据id获取广告位
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AdvertisementPosition getAdvertisementPositionById(String id) throws Exception;
	
	
	/**
	 * 根据名称获取广告位信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	AdvertisementPosition getAdvertisementPositionByName(String name) throws Exception;
}
