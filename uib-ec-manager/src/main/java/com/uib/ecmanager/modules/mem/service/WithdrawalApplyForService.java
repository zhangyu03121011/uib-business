/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.mem.dao.WithdrawalApplyForDao;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.mem.entity.WithdrawalApplyFor;

/**
 * 提现管理表Service
 * @author luogc
 * @version 2016-07-28
 */
@Service
@Transactional(readOnly = true)
public class WithdrawalApplyForService extends CrudService<WithdrawalApplyForDao, WithdrawalApplyFor> {

	@Autowired
	private MemMemberService memMemberService;
	public WithdrawalApplyFor get(String id) {
		return super.get(id);
	}
	
	public List<WithdrawalApplyFor> findList(WithdrawalApplyFor withdrawalApplyFor) {
		return super.findList(withdrawalApplyFor);
	}
	
	public Page<WithdrawalApplyFor> findPage(Page<WithdrawalApplyFor> page, WithdrawalApplyFor withdrawalApplyFor) {
		return super.findPage(page, withdrawalApplyFor);
	}
	
	public Page<WithdrawalApplyFor> findPages(Page<WithdrawalApplyFor> page, WithdrawalApplyFor withdrawalApplyFor) {
		return super.findPages(page, withdrawalApplyFor);
	}
	@Transactional(readOnly = false)
	public void save(WithdrawalApplyFor withdrawalApplyFor) {
		super.save(withdrawalApplyFor);
	}
	
	@Transactional(readOnly = false)
	public void update(WithdrawalApplyFor withdrawalApplyFor) {
		super.update(withdrawalApplyFor);
		if(withdrawalApplyFor.getApplyStatus().equals("2")){
			MemMember memMember = new MemMember();
			memMember.setId(withdrawalApplyFor.getMemberId());
			List<MemMember> memMembers = memMemberService.findList(memMember);
			BigDecimal acount = memMembers.get(0).getCommission();
			acount = acount.subtract(withdrawalApplyFor.getApplyAmount());
			memMember.setCommission(acount);
			memMember.setId(memMembers.get(0).getId());
			memMember.setUpdateDate(new Date());
			memMemberService.updateById(memMember);
		}
			
	}
	
	@Transactional(readOnly = false)
	public void delete(WithdrawalApplyFor withdrawalApplyFor) {
		super.delete(withdrawalApplyFor);
	}
	
}