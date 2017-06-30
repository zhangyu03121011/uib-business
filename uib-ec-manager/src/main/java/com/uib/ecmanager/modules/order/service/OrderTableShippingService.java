/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.order.dao.OrderShippingRefDao;
import com.uib.ecmanager.modules.order.dao.OrderTableShippingDao;
import com.uib.ecmanager.modules.order.dao.OrderTableShippingItemDao;
import com.uib.ecmanager.modules.order.entity.OrderShippingRef;
import com.uib.ecmanager.modules.order.entity.OrderTableItem;
import com.uib.ecmanager.modules.order.entity.OrderTableShipping;
import com.uib.ecmanager.modules.order.entity.OrderTableShippingItem;
import com.uib.ecmanager.modules.sys.entity.User;

/**
 * 发货单Service
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderTableShippingService extends CrudService<OrderTableShippingDao, OrderTableShipping> {

	@Autowired
	private OrderShippingRefDao orderShippingRefDao;
	@Autowired
	private OrderTableShippingItemDao orderTableShippingItemDao;
	@Autowired
	private OrderTableShippingDao orderTableShippingDao;
	
	public OrderTableShipping get(String id) {
		OrderTableShipping orderTableShipping = super.get(id);
		orderTableShipping.setOrderShippingRefList(orderShippingRefDao.findList(new OrderShippingRef(orderTableShipping)));
		orderTableShipping.setOrderTableShippingItemList(orderTableShippingItemDao.findList(new OrderTableShippingItem(orderTableShipping)));
		return orderTableShipping;
	}
	
	public List<OrderTableShipping> findList(OrderTableShipping orderTableShipping) {
		return super.findList(orderTableShipping);
	}
	
	public Page<OrderTableShipping> findPage(Page<OrderTableShipping> page, OrderTableShipping orderTableShipping) {
		return super.findPage(page, orderTableShipping);
	}
	
	@Transactional(readOnly = false)
	public void save(OrderTableShipping orderTableShipping) {
		super.save(orderTableShipping);
		for (OrderShippingRef orderShippingRef : orderTableShipping.getOrderShippingRefList()){
			if (orderShippingRef.getId() == null){
				continue;
			}
			if (OrderShippingRef.DEL_FLAG_NORMAL.equals(orderShippingRef.getDelFlag())){
				if (StringUtils.isBlank(orderShippingRef.getId())){
					orderShippingRef.setOrderTableShipping(orderTableShipping);
					orderShippingRef.preInsert();
					orderShippingRefDao.insert(orderShippingRef);
				}
			}else{
				orderShippingRefDao.delete(orderShippingRef);
			}
		}
		for (OrderTableShippingItem orderTableShippingItem : orderTableShipping.getOrderTableShippingItemList()){
			if (orderTableShippingItem.getId() == null){
				continue;
			}
			if (OrderTableShippingItem.DEL_FLAG_NORMAL.equals(orderTableShippingItem.getDelFlag())){
				if (StringUtils.isBlank(orderTableShippingItem.getId())){
					orderTableShippingItem.setOrderTableShipping(orderTableShipping);
					orderTableShippingItem.preInsert();
					orderTableShippingItemDao.insert(orderTableShippingItem);
				}
			}else{
				orderTableShippingItemDao.delete(orderTableShippingItem);
			}
		}
	}
	
	
	@Transactional(readOnly = false)
	public void update(OrderTableShipping orderTableShipping) {
		super.update(orderTableShipping);
		for (OrderShippingRef orderShippingRef : orderTableShipping.getOrderShippingRefList()){
			if (orderShippingRef.getId() == null){
				continue;
			}
			if (OrderShippingRef.DEL_FLAG_NORMAL.equals(orderShippingRef.getDelFlag())){
				
					orderShippingRef.preUpdate();
					orderShippingRefDao.update(orderShippingRef);
				
			}else{
				orderShippingRefDao.delete(orderShippingRef);
			}
		}
		for (OrderTableShippingItem orderTableShippingItem : orderTableShipping.getOrderTableShippingItemList()){
			if (orderTableShippingItem.getId() == null){
				continue;
			}
			if (OrderTableShippingItem.DEL_FLAG_NORMAL.equals(orderTableShippingItem.getDelFlag())){
				
					orderTableShippingItem.preUpdate();
					orderTableShippingItemDao.update(orderTableShippingItem);
				
			}else{
				orderTableShippingItemDao.delete(orderTableShippingItem);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(OrderTableShipping orderTableShipping) {
		super.delete(orderTableShipping);
		orderShippingRefDao.delete(new OrderShippingRef(orderTableShipping));
		orderTableShippingItemDao.delete(new OrderTableShippingItem(orderTableShipping));
	}
	
	@Transactional(readOnly = false)
	public List<OrderTableShippingItem> findShippingItemList(String shippingId){
		return orderTableShippingItemDao.findShippingItemByShippingId(shippingId);
	}
	
	@Transactional(readOnly = false)
	public void saveShippingItem(OrderTableShippingItem otsi){
		 orderTableShippingItemDao.insert(otsi);
	}
	@Transactional(readOnly = false)
	public void saveShipping(OrderTableShipping shipping){
		 orderTableShippingDao.insert(shipping);
	}
	@Transactional(readOnly = false)
	public void saveShippingRef(OrderShippingRef osf){
		 orderShippingRefDao.insert(osf);
	}
	
}