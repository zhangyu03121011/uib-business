/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.mem.dao.MemRankDao;
import com.uib.ecmanager.modules.mem.entity.MemRank;

/**
 * 会员等级(单表)Service
 * @author kevin
 * @version 2015-05-29
 */
@Service
@Transactional(readOnly = true)
public class MemRankService extends CrudService<MemRankDao, MemRank> {

	public MemRank get(String id) {
		return super.get(id);
	}
	
	public List<MemRank> findList(MemRank memRank) {
		return super.findList(memRank);
	}
	
	public Page<MemRank> findPage(Page<MemRank> page, MemRank memRank) {
		return super.findPage(page, memRank);
	}
	
	@Transactional(readOnly = false)
	public void save(MemRank memRank) {
		super.save(memRank);
	}
	
	@Transactional(readOnly = false)
	public void update(MemRank memRank) {
		super.update(memRank);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemRank memRank) {
		super.delete(memRank);
	}
	
	@Transactional(readOnly = true)
	public MemRank findByAmount(BigDecimal amount){
		if (amount == null) {
			return null;
		}
		return dao.findByAmount(amount);
	}

	@Transactional(readOnly = true)
	public boolean nameExists(String name) {
		if (name == null) {
			return false;
		}
		Long count = dao.nameExists(name);
		return count > 0;
	}

	public boolean amountExists(BigDecimal amount) {
		if (amount == null) {
			return false;
		}
		Long count = dao.amountExists(amount);
		return count > 0;
	}

	public MemRank findDefault() {
		try {
			return dao.findDefault();
		} catch (Exception e) {
			return null;
		}
	}
	
	public int findMemberByMemRankId(String id) {
		int count = dao.findMemberByMemRankId(id);
		return count;
	}
}