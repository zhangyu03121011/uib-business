/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.service.TreeService;
import com.uib.ecmanager.modules.sys.dao.AreaDao;
import com.uib.ecmanager.modules.sys.entity.Area;
import com.uib.ecmanager.modules.sys.entity.Office;
import com.uib.ecmanager.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}
	
	public List<Area> findAllList(Area area){
		return super.findList(area);
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public List<Area> findcityList(String parentId){
		return dao.findAreasByParentId(parentId);
	}

	@Transactional(readOnly = false)
	public List<Area> ProductfindAllList(Area area){
		return dao.ProductfindAllList(area);
	}
}
