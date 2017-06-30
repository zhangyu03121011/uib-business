/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.order.dao.OrderTablePaymentDao;
import com.uib.ecmanager.modules.order.entity.OrderTablePayment;

/**
 * 收款单Service
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderTablePaymentService extends CrudService<OrderTablePaymentDao, OrderTablePayment> {

	public OrderTablePayment get(String id) {
		return super.get(id);
	}
	
	public List<OrderTablePayment> findList(OrderTablePayment orderTablePayment) {
		return super.findList(orderTablePayment);
	}
	
	public Page<OrderTablePayment> findPage(Page<OrderTablePayment> page, OrderTablePayment orderTablePayment) {
		return super.findPage(page, orderTablePayment);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderTablePayment orderTablePayment) {
		super.save(orderTablePayment);
	}
	
	@Transactional(readOnly = false)
	public void update(OrderTablePayment orderTablePayment) {
		super.update(orderTablePayment);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderTablePayment orderTablePayment) {
		super.delete(orderTablePayment);
	}
	
}