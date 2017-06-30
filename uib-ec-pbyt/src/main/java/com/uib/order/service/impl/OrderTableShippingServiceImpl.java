package com.uib.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.common.utils.UUIDGenerator;
import com.uib.order.dao.DeliveryCorporationDao;
import com.uib.order.dao.OrderShippingRefDao;
import com.uib.order.dao.OrderTableShippingDao;
import com.uib.order.dao.OrderTableShippingItemDao;
import com.uib.order.entity.DeliveryCorporation;
import com.uib.order.entity.OrderShippingRef;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTableItem;
import com.uib.order.entity.OrderTableShipping;
import com.uib.order.entity.OrderTableShippingItem;
import com.uib.order.entity.ShippingMethod;
import com.uib.order.service.OrderTableShippingService;
import com.uib.order.service.ShippingMethodService;
import com.uib.serviceUtils.OrderNoGenerateUtil;

@Service
public class OrderTableShippingServiceImpl implements OrderTableShippingService{
	@Autowired
	private ShippingMethodService shippingMethodService;
	@Autowired
	private OrderTableShippingDao shippingDao;
	@Autowired
	private OrderTableShippingItemDao shippingItemDao;
	@Autowired
	private DeliveryCorporationDao corporationDao;
	@Autowired
	private OrderShippingRefDao shippingRefDao;

	@Override
	public void addOrderTableShipping(OrderTable order) throws Exception{
		OrderTableShipping shipping = new OrderTableShipping();
		shipping.setId(UUIDGenerator.getUUID());
		shipping.setAddress(order.getAddress());
		shipping.setArea(order.getArea());
		shipping.setConsignee(order.getConsignee());
		shipping.setCreateBy(order.getCreateBy());
		shipping.setUpdateBy(order.getUpdateBy());
		shipping.setCreateDate(new Date());
		shipping.setDelFlag("0");
		ShippingMethod shippingMethod = shippingMethodService.getShippingMethod(order.getShippingMethod());
		DeliveryCorporation corporation = corporationDao.getDeliveryCorporation(shippingMethod.getDefaultdeliverycorp().getId());
		shipping.setDeliveryCorp(corporation!=null?corporation.getName():null);
		shipping.setDeliveryCorpCode(corporation.getCode());
		shipping.setDeliveryCorpUrl(corporation.getUrl());
		shipping.setFreight(order.getFreight());
		shipping.setOrderNo(order.getOrderNo());
		shipping.setPhone(order.getPhone());
		shipping.setShippingMethod(shippingMethod.getName());
		shipping.setShippingNo("E"+OrderNoGenerateUtil.getOrderNo());
		shipping.setZipCode(order.getZipCode());
		shipping.setUpdateDate(new Date());
//		shipping.setOrderShippingRefList(orderShippingRefList);
		OrderShippingRef shippingRef = new OrderShippingRef();
		shippingRef.setId(UUIDGenerator.getUUID());
		shippingRef.setOrderTabelId(order.getId());
		shippingRef.setOrderTableShipping(shipping);
//		shipping.setOrderTableShippingItemList(orderTableShippingItemList);
		List<OrderTableShippingItem> shippingItemList = new ArrayList<OrderTableShippingItem>();
		List<OrderTableItem> orderItems = order.getList_ordertable_item();
		shippingDao.insert(shipping);
		shippingRefDao.insert(shippingRef);
		for (OrderTableItem orderTableItem : orderItems) {
			OrderTableShippingItem item = new OrderTableShippingItem();
			item.setId(UUIDGenerator.getUUID());
			item.setName(orderTableItem.getName());
			item.setOrderTableShipping(shipping);
			item.setProductNo(orderTableItem.getGoodsNo());
			item.setQuantity(orderTableItem.getQuantity());
			item.setShippingNo(shipping.getShippingNo());
			item.setCreateBy(shipping.getCreateBy());
			item.setUpdateBy(shipping.getUpdateBy());
			item.setCreateDate(shipping.getCreateDate());
			item.setUpdateDate(shipping.getUpdateDate());
			item.setDelFlag("0");
			shippingItemList.add(item);
			shippingItemDao.insert(item);
		}
	}

	@Override
	public void deleteOrderTableShipping(Map<String, String> params)
			throws Exception {
		shippingDao.delete(params);
	}

	@Override
	public void updateDeleteFlag(Map<String, String> params) throws Exception {
		shippingDao.updateDeleteFlag(params);
	}

}
