/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.supplier.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.supplier.dao.SupplierDao;
import com.uib.ecmanager.modules.supplier.entity.Supplier;

/**
 * 供应商管理Service
 * @author luogc
 * @version 2016-08-22
 */
@Service
@Transactional(readOnly = true)
public class SupplierService extends CrudService<SupplierDao, Supplier> {

	public Supplier get(String id) {
		return super.get(id);
	}
	
	public List<Supplier> findList(Supplier supplier) {
		return super.findList(supplier);
	}
	
	public Page<Supplier> findPage(Page<Supplier> page, Supplier supplier) {
		return super.findPage(page, supplier);
	}
	
	@Transactional(readOnly = false)
	public void save(Supplier supplier) {
		super.save(supplier);
	}
	
	@Transactional(readOnly = false)
	public void update(Supplier supplier) {
		super.update(supplier);
	}
	
	@Transactional(readOnly = false)
	public void delete(Supplier supplier) {
		super.delete(supplier);
	}
	
}