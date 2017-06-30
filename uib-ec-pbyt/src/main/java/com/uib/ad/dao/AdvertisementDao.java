package com.uib.ad.dao;

import java.util.List;
import java.util.Map;

import com.uib.ad.entity.Advertisement;

public interface AdvertisementDao {
	
	/**
	 * 根据广告位获取广告
	 * @param adPositionId
	 * @return
	 * @throws Exception
	 */
	List<Advertisement> getByAdPositionId(String adPositionId) throws Exception;
	
	List<Map<String,Object>> getAdvertisementImage(Map<String, Object> map) throws Exception;
	
	List<Map<String,Object>> getAppAdvertisementImage(Map<String, Object> map) throws Exception;
	
}
