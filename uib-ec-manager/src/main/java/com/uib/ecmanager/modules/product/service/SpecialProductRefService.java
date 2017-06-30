/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.product.entity.SpecialProductRef;
import com.uib.ecmanager.modules.product.dao.SpecialProductRefDao;

/**
 * 商品专题关联列表Service
 * @author limy
 * @version 2016-07-14
 */
@Service
@Transactional(readOnly = true)
public class SpecialProductRefService extends CrudService<SpecialProductRefDao, SpecialProductRef> {

	@Autowired
	private SpecialProductRefDao specialProdictRefDao;
	
	
	public SpecialProductRef get(String id) {
		return super.get(id);
	}
	
	public List<SpecialProductRef> findList(SpecialProductRef specialProductRef) {
		return super.findList(specialProductRef);
	}
	
	public Page<SpecialProductRef> findPage(Page<SpecialProductRef> page, SpecialProductRef specialProductRef) {
		return super.findPage(page, specialProductRef);
	}
	
	@Transactional(readOnly = false)
	public void save(SpecialProductRef specialProductRef) {
		super.save(specialProductRef);
	}
	
	@Transactional(readOnly = false)
	public void update(SpecialProductRef specialProductRef) {
		super.update(specialProductRef);
	}
	
	@Transactional(readOnly = false)
	public void delete(SpecialProductRef specialProductRef) {
		super.delete(specialProductRef);
	}

	
	@Transactional(readOnly = false)
	public void insertSpecialProductRef(List<SpecialProductRef> sprList){
		specialProdictRefDao.insertSpecialProductRef(sprList);
	}
}