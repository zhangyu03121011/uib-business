/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.mem.dao.MemMemberDao;
import com.uib.ecmanager.modules.mem.entity.MemMember;

/**
 * 会员表Service
 * @author kevin
 * @version 2015-05-31
 */
@Service
@Transactional(readOnly = true)
public class MemMemberService extends CrudService<MemMemberDao, MemMember> {
	@Autowired
	MemMemberDao memMemberDao;
	
	public MemMember get(String id) {
		MemMember memMember = super.get(id);
		return memMember;
	}
	
	public List<MemMember> findList(MemMember memMember) {
		return super.findList(memMember);
	}
	
	public Page<MemMember> findPage(Page<MemMember> page, MemMember memMember) {
		return super.findPage(page, memMember);
	}
	
	/**
	 * 自定义会员审核列表带分页
	 * @param page
	 * @param memMember
	 * @return
	 */
	public Page<MemMember> findMemApprovePage(Page<MemMember> page, MemMember memMember) {
		memMember.setPage(page);
		page.setList(memMemberDao.findMemApproveList(memMember));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(MemMember memMember) {
		super.save(memMember);
	}
	
	
	@Transactional(readOnly = false)
	public void update(MemMember memMember) {
		super.update(memMember);
	}
	
	@Transactional(readOnly = false)
	public void delete(MemMember memMember) {
		super.delete(memMember);
	}
	
	@Transactional(readOnly=false)
	public void updateVarifyStatus(MemMember member){
		super.update(member);
	}

	public void updateById(MemMember memMember) {
		memMemberDao.updateById(memMember);
		
	}

}