package com.uib.pay.dao.impl;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.pay.dao.PayDao;
import com.uib.pay.dto.WeixinPayResDto;

/**
 * 订单管理
 * 
 * @author kevin
 *
 */
@Component
public class PayDaoImpl extends MyBatisDao<WeixinPayResDto> implements PayDao {


	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(WeixinPayResDto weixinPayResDto) throws Exception {
		this.save("savePayInfo", weixinPayResDto);
	}
}
