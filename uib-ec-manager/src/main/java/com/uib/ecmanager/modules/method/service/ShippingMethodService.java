/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.method.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.method.dao.ShippingMethodDao;
import com.uib.ecmanager.modules.method.entity.ShippingMethod;

/**
 * 配送方式Service
 * @author limy
 * @version 2015-07-15
 */
@Service
@Transactional(readOnly = true)
public class ShippingMethodService extends CrudService<ShippingMethodDao, ShippingMethod> {

	@Autowired
	private ShippingMethodDao shippingMethodDao;
	
	public ShippingMethod get(String id) {
		return super.get(id);
	}
	
	public List<ShippingMethod> findList(ShippingMethod shippingMethod) {
		return super.findList(shippingMethod);
	}
	
	public Page<ShippingMethod> findPage(Page<ShippingMethod> page, ShippingMethod shippingMethod) {
		return super.findPage(page, shippingMethod);
	}
	
	@Transactional(readOnly = false)
	public void save(ShippingMethod shippingMethod) {
		super.save(shippingMethod);
	}
	
	@Transactional(readOnly = false)
	public void update(ShippingMethod shippingMethod) {
		super.update(shippingMethod);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShippingMethod shippingMethod) {
		super.delete(shippingMethod);
	}
	
	@Transactional(readOnly = false)
	public ShippingMethod getShippingMethodByName(String shippingMethodName) {
		return shippingMethodDao.getShippingMethodByName(shippingMethodName);
	}
	
}