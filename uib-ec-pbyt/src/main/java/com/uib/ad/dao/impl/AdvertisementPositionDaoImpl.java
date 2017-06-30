package com.uib.ad.dao.impl;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.ad.dao.AdvertisementPositionDao;
import com.uib.ad.entity.AdvertisementPosition;

@Component
public class AdvertisementPositionDaoImpl extends MyBatisDao<AdvertisementPosition> implements AdvertisementPositionDao{

	@Override
	public AdvertisementPosition getAdvertisementPositionById(String id) throws Exception {
		return this.getUnique("getAdvertisementPositionById", id);
	}

	@Override
	public AdvertisementPosition getAdvertisementPositionByName(String name) throws Exception {
		return this.getUnique("getAdvertisementPositionByName", name);
	}
	
	
}
