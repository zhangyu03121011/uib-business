package com.uib.union.payment.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.union.payment.dao.RefundOrderDao;
import com.uib.union.payment.pojo.RefundOrder;

@Component
public class RefundOrderDaoImpl extends MyBatisDao<RefundOrder> implements RefundOrderDao{

	@Override
	public void saveRefundOrder(RefundOrder refundOrder) throws Exception {
		this.save("saveRefundOrder", refundOrder);
	}

	@Override
	public void updateRefundOrder(Map<String, String> map) throws Exception {
		this.update("updateRefundOrder" , map);
	}

	@Override
	public RefundOrder getRefundOrderByRefundNo(String refundNo) throws Exception {
		return this.getUnique("getRefundOrderByRefundNo", refundNo);
	}

	@Override
	public RefundOrder getRefundOrderByPaymentNo(String paymentNo) throws Exception {
		return this.getUnique("getRefundOrderByPaymentNo", paymentNo);
	}

}
