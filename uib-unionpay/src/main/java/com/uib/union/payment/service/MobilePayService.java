package com.uib.union.payment.service;

import java.util.Map;

import com.uib.core.exception.GenericException;
import com.uib.union.payment.dto.AcpsdkPayResponse;
import com.uib.union.payment.dto.MerchantNotifyDto;
import com.uib.union.payment.dto.PaymentOrderDto;

/**
 * 手机支付
 * @author kevin
 *
 */
public interface MobilePayService {
	
	/**
	 * 保存手机用户选择银联支付渠道数据
	 * @param paymentOrderDto
	 * @return
	 * @throws Exception
	 */
	Map<String, String> saveMobilePay(PaymentOrderDto paymentOrderDto) throws Exception;
	
	
	
	
	/**
	 * 接收银联全渠道返回接口数据
	 * @param unionAllPayNotify
	 * @return 
	 * @throws GenericException
	 */
	MerchantNotifyDto unionAllPayNotify(AcpsdkPayResponse acpsdkPayResponse) throws Exception;

}
