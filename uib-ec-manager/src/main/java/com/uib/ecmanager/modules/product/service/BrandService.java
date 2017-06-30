/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.product.dao.BrandDao;
import com.uib.ecmanager.modules.product.entity.Brand;

/**
 * 商品品牌Service
 * @author gaven
 * @version 2015-06-13
 */
@Service
@Transactional(readOnly = true)
public class BrandService extends CrudService<BrandDao, Brand> {

	public Brand get(String id) {
		return super.get(id);
	}
	
	public List<Brand> findList(Brand brand) {
		return super.findList(brand);
	}
	
	public Page<Brand> findPage(Page<Brand> page, Brand brand) {
		return super.findPage(page, brand);
	}
	
	@Transactional(readOnly = false)
	public void save(Brand brand) {
		super.save(brand);
	}
	
	@Transactional(readOnly = false)
	public void update(Brand brand) {
		super.update(brand);
	}
	
	@Transactional(readOnly = false)
	public void delete(Brand brand) {
		super.delete(brand);
	}
	
}