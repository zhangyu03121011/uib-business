package com.uib.ad.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.ad.dao.AdvertisementDao;
import com.uib.ad.dao.AdvertisementPositionDao;
import com.uib.ad.entity.Advertisement;
import com.uib.ad.entity.AdvertisementPosition;
import com.uib.ad.service.AdvertisementService;
import com.uib.common.web.Global;

@Component
public class AdvertisementServiceImpl implements AdvertisementService{
	
	@Autowired
	private AdvertisementPositionDao advertisementPositionDao;
	
	@Autowired
	private AdvertisementDao advertisementDao;
	
	@Override
	public AdvertisementPosition getAdPositionById(String id) throws Exception {
		return advertisementPositionDao.getAdvertisementPositionById(id);
	}

	@Override
	public List<Advertisement> getByAdPositionId(String adPositionId) throws Exception {
		return advertisementDao.getByAdPositionId(adPositionId);
	}

	@Override
	public AdvertisementPosition getAdvertisementPositionByName(String name) throws Exception {
		return advertisementPositionDao.getAdvertisementPositionByName(name);
	}
	
	@Override
	public List<Map<String,Object>> getAdvertisementImage(String adPositionId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adPositionId", adPositionId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return advertisementDao.getAdvertisementImage(map);
	}
	
	@Override
	public List<Map<String,Object>> getAppAdvertisementImage(String adPositionId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("adPositionId", adPositionId);
		map.put("imageUrl", Global.getConfig("upload.image.path"));
		return advertisementDao.getAppAdvertisementImage(map);
	}

}
