/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.product.entity.Special;
import com.uib.ecmanager.modules.product.dao.SpecialDao;
import com.uib.ecmanager.modules.product.dao.SpecialProductRefDao;

/**
 * 专题信息Service
 * @author limy
 * @version 2016-07-14
 */
@Service
@Transactional(readOnly = true)
public class SpecialService extends CrudService<SpecialDao, Special> {

	@Autowired
	private SpecialProductRefDao specialProductRefDao;
	
	@Autowired
	private SpecialDao specialDao;
	
	public Special get(String id) {
		return super.get(id);
	}
	
	public List<Special> findList(Special special) {
		return super.findList(special);
	}
	
	public Page<Special> findPage(Page<Special> page, Special special) {
		return super.findPage(page, special);
	}
	
	@Transactional(readOnly = false)
	public void save(Special special) {
		super.save(special);
	}
	
	@Transactional(readOnly = false)
	public void update(Special special) {
		super.update(special);
	}
	
	@Transactional(readOnly = false)
	public void delete(Special special) {
		super.delete(special);
		specialProductRefDao.deleteRef(special.getId());
	}
	
	@Transactional(readOnly = false)
	public Integer getbysort(String sort){
		return specialDao.getbysort(sort);
	}
	
	@Transactional(readOnly = false)
	public void updateFlag(String id){
		specialDao.updateFlag(id);
	}
}