/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.order.dao.OrderTableRefundsDao;
import com.uib.ecmanager.modules.order.entity.OrderTableRefunds;

/**
 * 退款单Service
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderTableRefundsService extends CrudService<OrderTableRefundsDao, OrderTableRefunds> {

	public OrderTableRefunds get(String id) {
		return super.get(id);
	}
	
	public List<OrderTableRefunds> findList(OrderTableRefunds orderTableRefunds) {
		return super.findList(orderTableRefunds);
	}
	
	public Page<OrderTableRefunds> findPage(Page<OrderTableRefunds> page, OrderTableRefunds orderTableRefunds) {
		return super.findPage(page, orderTableRefunds);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderTableRefunds orderTableRefunds) {
		super.save(orderTableRefunds);
	}
	
	@Transactional(readOnly = false)
	public void update(OrderTableRefunds orderTableRefunds) {
		super.update(orderTableRefunds);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderTableRefunds orderTableRefunds) {
		super.delete(orderTableRefunds);
	}
	
}