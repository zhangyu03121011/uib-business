/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.modules.order.dao.OrderShippingRefDao;
import com.uib.ecmanager.modules.order.entity.OrderReturnsRef;
import com.uib.ecmanager.modules.order.entity.OrderShippingRef;

/**
 * 订单与发货关联Service
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderShippingRefService extends CrudService<OrderShippingRefDao, OrderShippingRef> {

	public OrderShippingRef get(String id) {
		return super.get(id);
	}
	
	public List<OrderShippingRef> findList(OrderShippingRef orderShippingRef) {
		return super.findList(orderShippingRef);
	}
	
	public Page<OrderShippingRef> findPage(Page<OrderShippingRef> page, OrderShippingRef orderShippingRef) {
		return super.findPage(page, orderShippingRef);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderShippingRef orderShippingRef) {
		super.save(orderShippingRef);
	}
	
	@Transactional(readOnly = false)
	public void update(OrderShippingRef orderShippingRef) {
		super.update(orderShippingRef);
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderShippingRef orderShippingRef) {
		super.delete(orderShippingRef);
	}
	@Transactional(readOnly = false)
	public List<OrderShippingRef> findShippingRefList(String orderNo){
		return dao.findShippingRefByOrderNo(orderNo);
	}
}