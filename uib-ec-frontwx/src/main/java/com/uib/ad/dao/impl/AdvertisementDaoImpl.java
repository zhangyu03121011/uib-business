package com.uib.ad.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.ad.dao.AdvertisementDao;
import com.uib.ad.entity.Advertisement;
import com.uib.core.dao.MyBatisDao;

@Component
public class AdvertisementDaoImpl extends MyBatisDao<Advertisement> implements AdvertisementDao{

	@Override
	public List<Advertisement> getByAdPositionId(String adPositionId) throws Exception {
		return this.getObjectList("getByAdPositionId", adPositionId);
	}
	
	@Override
	public List<Map<String,Object>> getAdvertisementImage(Map<String, Object> map) throws Exception {
		return this.getList("getAdvertisementImage", map);
	}
	
	@Override
	public List<Map<String,Object>> getAppAdvertisementImage(Map<String, Object> map) throws Exception {
		return this.getList("getAppAdvertisementImage", map);
	}
}
