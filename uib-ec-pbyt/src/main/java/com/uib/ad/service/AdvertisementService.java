package com.uib.ad.service;

import java.util.List;
import java.util.Map;

import com.uib.ad.entity.Advertisement;
import com.uib.ad.entity.AdvertisementPosition;

/**
 * 广告及广告位管理service
 * @author kevin
 *
 */
public interface AdvertisementService {
	
	/**
	 * 根据ID获取广告位
	 * @param id
	 * @return
	 * @throws Exception
	 */
	AdvertisementPosition getAdPositionById(String id) throws Exception;
	
	
	/**
	 * 根据广告位获取广告
	 * @param adPositionId
	 * @return
	 * @throws Exception
	 */
	List<Advertisement> getByAdPositionId(String adPositionId) throws Exception;
	
	
	/**
	 * 根据广告位获取首页广告
	 * @param adPositionId
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getAdvertisementImage(String adPositionId) throws Exception;
	
	List<Map<String,Object>> getAppAdvertisementImage(String adPositionId) throws Exception;
	
	
	/**
	 * 根据广告位名称获取广告位信息
	 * @param name
	 * @return
	 * @throws Exception
	 */
	AdvertisementPosition getAdvertisementPositionByName(String name) throws Exception;
}
