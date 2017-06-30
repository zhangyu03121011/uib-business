/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.ad.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.ad.dao.AdvertisementPositionDao;
import com.uib.ecmanager.modules.ad.entity.AdvertisementPosition;

/**
 * 广告位Service
 * @author gaven
 * @version 2015-06-06
 */
@Service
@Transactional(readOnly = true)
public class AdvertisementPositionService extends CrudService<AdvertisementPositionDao, AdvertisementPosition> {
	private static final String NORMAL_DEL_FLAG = "0";

	public AdvertisementPosition get(String id) {
		return super.get(id);
	}
	
	public List<AdvertisementPosition> findList() {
		AdvertisementPosition advertisementPosition = new AdvertisementPosition();
		advertisementPosition.setDelFlag(NORMAL_DEL_FLAG);
		return super.findList(advertisementPosition);
	}
	
	public List<AdvertisementPosition> findList(AdvertisementPosition advertisementPosition) {
		return super.findList(advertisementPosition);
	}
	
	public Page<AdvertisementPosition> findPage(Page<AdvertisementPosition> page, AdvertisementPosition advertisementPosition) {
		return super.findPage(page, advertisementPosition);
	}
	
	@Transactional(readOnly = false)
	public void save(AdvertisementPosition advertisementPosition) {
		super.save(advertisementPosition);
	}
	
	@Transactional(readOnly = false)
	public void update(AdvertisementPosition advertisementPosition) {
		super.update(advertisementPosition);
	}
	
	@Transactional(readOnly = false)
	public void delete(AdvertisementPosition advertisementPosition) {
		super.delete(advertisementPosition);
	}
	
}