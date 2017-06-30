/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uib.ecmanager.common.persistence.Page;
import com.uib.ecmanager.common.service.CrudService;
import com.uib.ecmanager.common.utils.StringUtils;
import com.uib.ecmanager.modules.mem.entity.WithdrawalApplyFor;
import com.uib.ecmanager.modules.mem.service.WithdrawalApplyForService;
import com.uib.ecmanager.modules.order.dao.OrderReturnsRefDao;
import com.uib.ecmanager.modules.order.dao.OrderTableDao;
import com.uib.ecmanager.modules.order.dao.OrderTableReturnsDao;
import com.uib.ecmanager.modules.order.dao.OrderTableReturnsItemDao;
import com.uib.ecmanager.modules.order.entity.OrderReturnsRef;
import com.uib.ecmanager.modules.order.entity.OrderTable;
import com.uib.ecmanager.modules.order.entity.OrderTableReturns;
import com.uib.ecmanager.modules.order.entity.OrderTableReturnsItem;

/**
 * 退货单Service
 * 
 * @author limy
 * @version 2015-06-08
 */
@Service
@Transactional(readOnly = true)
public class OrderTableReturnsService extends
		CrudService<OrderTableReturnsDao, OrderTableReturns> {

	@Autowired
	private OrderReturnsRefDao orderReturnsRefDao;
	@Autowired
	private OrderTableReturnsItemDao orderTableReturnsItemDao;
	@Autowired
	private OrderTableReturnsDao orderTableReturnsDao;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private OrderTableDao orderTableDao;
	@Autowired
	private WithdrawalApplyForService withdrawalApplyForService;
	@Value("${upload.image.path}")
	private String uploadImagePath;
	@Value("${frontWeb.image.baseUrl}")
	private String baseUrl;
	@Value("${frontweb.image.folder}")
	private String imageFolder;

	public OrderTableReturns get(String id) {
		OrderTableReturns orderTableReturns = super.get(id);
		orderTableReturns.setOrderReturnsRefList(orderReturnsRefDao
				.findList(new OrderReturnsRef(orderTableReturns)));
		List<OrderTableReturnsItem> tableReturnsItems = orderTableReturnsItemDao
				.findList(new OrderTableReturnsItem(orderTableReturns));
		for (OrderTableReturnsItem tableReturnsItem : tableReturnsItems) {
//			tableReturnsItem.setImgWebUrl(baseUrl + imageFolder
//					+ tableReturnsItem.getImage());
			tableReturnsItem.setImgWebUrl(baseUrl
					+ tableReturnsItem.getImage());
			tableReturnsItem.setImgDiskUrl(uploadImagePath
					+ tableReturnsItem.getImage());
		}
		orderTableReturns.setOrderTableReturnsItemList(tableReturnsItems);
		//设置用户的银行卡相关信息
		//1.根据订单号查询出memberId 2.根据memberId找寻出默认的绑定银行卡信息
		OrderTable orderTable=orderTableService.findOrderTableByOrderNo(orderTableReturns.getOrderNo());
		WithdrawalApplyFor withdrawalApplyFor=new WithdrawalApplyFor();
		withdrawalApplyFor.setMemberId(orderTable.getMemberNo());
		withdrawalApplyFor.setIsDefault("1");
		List<WithdrawalApplyFor> list=withdrawalApplyForService.findList(withdrawalApplyFor);
		if(list.size()!=0){
			orderTableReturns.setWithdrawalApplyFor(list.get(0));	
		}
		return orderTableReturns;
	}

	public List<OrderTableReturns> findList(OrderTableReturns orderTableReturns) {
		return super.findList(orderTableReturns);
	}

	public Page<OrderTableReturns> findPage(Page<OrderTableReturns> page,
			OrderTableReturns orderTableReturns) {
		Page<OrderTableReturns> tmpPage = super.findPage(page,
				orderTableReturns);
		for (OrderTableReturns orderTableReturn : tmpPage.getList()) {
			switch (orderTableReturn.getReturnType()) {
			case 1:
				orderTableReturn.setReturnTypeStr("退款");
				break;
			case 2:
				orderTableReturn.setReturnTypeStr("退货");
				break;
			case 3:
				orderTableReturn.setReturnTypeStr("换货");
				break;
			}
			switch (orderTableReturn.getReturnStatus()) {
			case 1:
				orderTableReturn.setReturnStatusStr("已处理");
				break;
			case 2:
				orderTableReturn.setReturnStatusStr("无法退货");
				break;
			case 3:
				orderTableReturn.setReturnStatusStr("未处理");
				break;
			}
		}
		return tmpPage;
	}

	@Transactional(readOnly = false)
	public void save(OrderTableReturns orderTableReturns) {
		super.save(orderTableReturns);
		for (OrderReturnsRef orderReturnsRef : orderTableReturns
				.getOrderReturnsRefList()) {
			if (orderReturnsRef.getId() == null) {
				continue;
			}
			if (OrderReturnsRef.DEL_FLAG_NORMAL.equals(orderReturnsRef
					.getDelFlag())) {
				if (StringUtils.isBlank(orderReturnsRef.getId())) {
					orderReturnsRef.setOrderTableReturns(orderTableReturns);
					orderReturnsRef.preInsert();
					orderReturnsRefDao.insert(orderReturnsRef);
				}
			} else {
				orderReturnsRefDao.delete(orderReturnsRef);
			}
		}
		for (OrderTableReturnsItem orderTableReturnsItem : orderTableReturns
				.getOrderTableReturnsItemList()) {
			if (orderTableReturnsItem.getId() == null) {
				continue;
			}
			if (OrderTableReturnsItem.DEL_FLAG_NORMAL
					.equals(orderTableReturnsItem.getDelFlag())) {
				if (StringUtils.isBlank(orderTableReturnsItem.getId())) {
					orderTableReturnsItem
							.setOrderTableReturns(orderTableReturns);
					orderTableReturnsItem.preInsert();
					orderTableReturnsItemDao.insert(orderTableReturnsItem);
				}
			} else {
				orderTableReturnsItemDao.delete(orderTableReturnsItem);
			}
		}
	}

	@Transactional(readOnly = false)
	public void update(OrderTableReturns orderTableReturns) {
		super.update(orderTableReturns);
		for (OrderReturnsRef orderReturnsRef : orderTableReturns
				.getOrderReturnsRefList()) {
			if (orderReturnsRef.getId() == null) {
				continue;
			}
			if (OrderReturnsRef.DEL_FLAG_NORMAL.equals(orderReturnsRef
					.getDelFlag())) {

				orderReturnsRef.preUpdate();
				orderReturnsRefDao.update(orderReturnsRef);

			} else {
				orderReturnsRefDao.delete(orderReturnsRef);
			}
		}
		for (OrderTableReturnsItem orderTableReturnsItem : orderTableReturns
				.getOrderTableReturnsItemList()) {
			if (orderTableReturnsItem.getId() == null) {
				continue;
			}
			if (OrderTableReturnsItem.DEL_FLAG_NORMAL
					.equals(orderTableReturnsItem.getDelFlag())) {

				orderTableReturnsItem.preUpdate();
				orderTableReturnsItemDao.update(orderTableReturnsItem);

			} else {
				orderTableReturnsItemDao.delete(orderTableReturnsItem);
			}
		}
	}

	@Transactional(readOnly = false)
	public void delete(OrderTableReturns orderTableReturns) {
		super.delete(orderTableReturns);
		orderReturnsRefDao.delete(new OrderReturnsRef(orderTableReturns));
		orderTableReturnsItemDao.delete(new OrderTableReturnsItem(
				orderTableReturns));
	}

	/**
	 * @param orderTableReturns
	 */
	@Transactional(readOnly = false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void updateStatus(OrderTableReturns orderTableReturns) {
		//已处理和无法退货时，更新订单状态为已完成 1.根据订单编号去查询订单id  2.根据订单id去更新订单状态
		int returnStatus=orderTableReturns.getReturnStatus();
		if(returnStatus==1 || returnStatus==2){
			OrderTable orderTable=orderTableService.findOrderTableByOrderNo(orderTableReturns.getOrderNo()); 
			orderTable.setOrderStatus("2");
			orderTableDao.update(orderTable);
		}
		orderTableReturnsDao.updateStatus(orderTableReturns);
	}

}