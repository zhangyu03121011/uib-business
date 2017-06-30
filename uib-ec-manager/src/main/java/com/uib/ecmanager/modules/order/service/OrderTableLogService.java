/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.order.dao.OrderTableLogDao;
import com.uib.ecmanager.modules.order.entity.OrderTableLog;

/**
 * 订单日志Service
 * @author limy
 * @version 2015-06-01
 */
@Service
@Transactional(readOnly = true)
public class OrderTableLogService extends CrudService<OrderTableLogDao, OrderTableLog> {

	public OrderTableLog get(String id) {
		return super.get(id);
	}
	
	public List<OrderTableLog> findList(OrderTableLog orderTableLog) {
		return super.findList(orderTableLog);
	}
	
	public Page<OrderTableLog> findPage(Page<OrderTableLog> page, OrderTableLog orderTableLog) {
		return super.findPage(page, orderTableLog);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderTableLog orderTableLog) {
		super.save(orderTableLog);
	}
	
	@Transactional(readOnly = false)
	public void update(OrderTableLog orderTableLog) {
		super.update(orderTableLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderTableLog orderTableLog) {
		super.delete(orderTableLog);
	}
	
}