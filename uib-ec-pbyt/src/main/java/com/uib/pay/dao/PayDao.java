package com.uib.pay.dao;

import com.uib.pay.dto.WeixinPayResDto;

public interface PayDao {

	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(WeixinPayResDto weixinPayResDto) throws Exception;
}
