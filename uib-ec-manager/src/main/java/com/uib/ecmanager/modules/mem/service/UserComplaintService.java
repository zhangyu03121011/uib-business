/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.mem.dao.UserComplaintDao;
import com.uib.ecmanager.modules.mem.entity.UserComplaint;

/**
 * 会员投诉Service
 * @author luogc
 * @version 2016-01-12
 */
@Service
@Transactional(readOnly = true)
public class UserComplaintService extends CrudService<UserComplaintDao, UserComplaint> {

	public UserComplaint get(String id) {
		return super.get(id);
	}
	
	public List<UserComplaint> findList(UserComplaint userComplaint) {
		return super.findList(userComplaint);
	}
	
	public Page<UserComplaint> findPage(Page<UserComplaint> page, UserComplaint userComplaint) {
		return super.findPage(page, userComplaint);
	}
	
	@Transactional(readOnly = false)
	public void save(UserComplaint userComplaint) {
		super.save(userComplaint);
	}
	
	@Transactional(readOnly = false)
	public void update(UserComplaint userComplaint) {
		super.update(userComplaint);
	}
	
	@Transactional(readOnly = false)
	public void delete(UserComplaint userComplaint) {
		super.delete(userComplaint);
	}
	
}