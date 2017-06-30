/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.navigation.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.navigation.dao.NavigationDao;
import com.uib.ecmanager.modules.navigation.entity.Navigation;

/**
 * 导航管理Service
 * @author gaven
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class NavigationService extends CrudService<NavigationDao, Navigation> {

	public Navigation get(String id) {
		return super.get(id);
	}
	
	public List<Navigation> findList(Navigation navigation) {
		return super.findList(navigation);
	}
	
	public Page<Navigation> findPage(Page<Navigation> page, Navigation navigation) {
		return super.findPage(page, navigation);
	}
	
	@Transactional(readOnly = false)
	public void save(Navigation navigation) {
		super.save(navigation);
	}
	
	@Transactional(readOnly = false)
	public void update(Navigation navigation) {
		super.update(navigation);
	}
	
	@Transactional(readOnly = false)
	public void delete(Navigation navigation) {
		super.delete(navigation);
	}
	
}