package com.uib.pay.service;

import java.util.Map;

import com.uib.common.web.B2CReq;
import com.uib.pay.dto.WeixinPayResDto;

public interface PayService {
	
	/**
	 * 处理订单支付成功返回通知数据
	 * @param tranData
	 * @throws Exception
	 */
	void disposeNotifyInfo(String tranData) throws Exception;

	
	/**
	 * 处理web支付请求
	 * @param b2cReq
	 * @throws Exception
	 */
	Map<String, Object> disposeWebPay(B2CReq b2cReq) throws Exception;
	
	/**
	 * 微信支付回调通知 更新订单、支付状态
	 * @param tranData
	 * @throws Exception
	 */
	public void weixinPayNotifyInfo(String orderNo) throws Exception;
	
	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(WeixinPayResDto weixinPayResDto) throws Exception;
}
