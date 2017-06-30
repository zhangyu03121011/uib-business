/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.mem.dao.MemMerchantDao;
import com.uib.ecmanager.modules.mem.entity.MemMerchant;
import com.uib.ecmanager.modules.sys.utils.UserUtils;

/**
 * 会员卖家信息Service
 * @author kevin
 * @version 2015-05-28
 */
@Service
@Transactional(readOnly = true)
public class MemMerchantService extends CrudService<MemMerchantDao, MemMerchant> {

	public MemMerchant get(String id) {
		return super.get(id);
	}
	
	public List<MemMerchant> findList(MemMerchant memMerchant) {
		return super.findList(memMerchant);
	}
	
	public Page<MemMerchant> findPage(Page<MemMerchant> page, MemMerchant memMerchant) {
		if(!UserUtils.getUser().isAdmin()){
			memMerchant.getSqlMap().put("userId", UserUtils.getUser().getId());
		}
		return super.findPage(page, memMerchant);
	}
	
	@Transactional(readOnly = false)
	public void save(MemMerchant memMerchant) {
		super.save(memMerchant);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemMerchant memMerchant) {
		super.delete(memMerchant);
	}
	
}