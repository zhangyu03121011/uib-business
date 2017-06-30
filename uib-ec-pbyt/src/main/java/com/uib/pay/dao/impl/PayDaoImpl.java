package com.uib.pay.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.member.entity.MemMember;
import com.uib.order.dao.OrderDao;
import com.uib.order.entity.OrderTable;
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
