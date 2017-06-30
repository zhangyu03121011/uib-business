/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.mem.dao.DepositDao;
import com.uib.ecmanager.modules.mem.entity.Deposit;

/**
 * 预存款Service
 * @author limy
 * @version 2015-06-15
 */
@Service
@Transactional(readOnly = true)
public class DepositService extends CrudService<DepositDao, Deposit> {

	public Deposit get(String id) {
		return super.get(id);
	}
	
	public List<Deposit> findList(Deposit deposit) {
		return super.findList(deposit);
	}
	
	public Page<Deposit> findPage(Page<Deposit> page, Deposit deposit) {
		return super.findPage(page, deposit);
	}
	
	@Transactional(readOnly = false)
	public void save(Deposit deposit) {
		super.save(deposit);
	}
	
	@Transactional(readOnly = false)
	public void update(Deposit deposit) {
		super.update(deposit);
	}
	
	@Transactional(readOnly = false)
	public void delete(Deposit deposit) {
		super.delete(deposit);
	}
	
}