package com.uib.union.payment.dao;

import java.util.Map;
import com.uib.union.payment.pojo.RefundOrder;

public interface RefundOrderDao {
	
	/**
	 * 保存退款信息
	 * @param refundOrder
	 * @throws Exception
	 */
	void saveRefundOrder(RefundOrder refundOrder) throws Exception ;
	
	
	/**
	 * 修改退货状态
	 * @param map
	 * @throws Exception
	 */
	void updateRefundOrder(Map<String, String> map) throws Exception;
	
	
	/**
	 * 根据退货订单号查询退货单信息
	 * @param refundNo
	 * @return
	 * @throws Exception
	 */
	RefundOrder getRefundOrderByRefundNo(String refundNo)  throws Exception;
	
	
	/**
	 * 根据原始支付流水号获取退货单信息
	 * @param paymentNo
	 * @return
	 * @throws Exception
	 */
	RefundOrder	getRefundOrderByPaymentNo(String paymentNo)  throws Exception;
	
}
