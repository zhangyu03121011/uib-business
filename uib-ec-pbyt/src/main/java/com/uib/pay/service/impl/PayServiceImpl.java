package com.uib.pay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easypay.common.utils.Base64Util;
import com.easypay.common.utils.HmacSHASign;
import com.easypay.common.utils.XmlUtil;
import com.easypay.common.web.B2CReq;
import com.easypay.common.web.B2CRes;
import com.uib.common.enums.EasyPayTranStatEnum;
import com.uib.common.enums.OrderStatus;
import com.uib.common.enums.PaymentStatusEnum;
import com.uib.order.entity.OrderTable;
import com.uib.order.entity.OrderTableItem;
import com.uib.order.service.OrderService;
import com.uib.pay.dao.PayDao;
import com.uib.pay.dto.WeixinPayResDto;
import com.uib.pay.service.PayService;
import com.uib.product.service.ProductService;

/**
 * 支付处理service
 * @author kevin
 *
 */
@Component
public class PayServiceImpl implements PayService{
	
	@Value("${UPOP.HMD5_PASSWORD}")
	private String md5;
	
	
	@Value("${MER_ID}")
	private String  MER_ID;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PayDao paydao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void disposeNotifyInfo(String tranData) throws Exception {
		B2CRes b2cRes = (B2CRes) XmlUtil.xmlStrToBean(tranData, B2CRes.class);
		//支付成功情况下修改订单状态以及商品库存
		if (b2cRes.getTranStat().equals(EasyPayTranStatEnum.ALREADY_PAY.getDesc())){
			OrderTable order =	orderService.getOrderByOrderNo(b2cRes.getOrderNo());
			List<OrderTableItem>  orderItemList =	order.getList_ordertable_item();
			if (null != orderItemList){
				for (OrderTableItem orderItem : orderItemList){
					productService.updateSubtractProductStock(orderItem.getQuantity(), orderItem.getGoodsNo());
				}
			}
			orderService.updateOrderStatusAndPayStatus(b2cRes.getOrderNo(), OrderStatus.paid_shipped.getIndex(),PaymentStatusEnum.paid.getIndex()); 
		}
	}

	@Override
	public Map<String, Object> disposeWebPay(B2CReq b2cReq) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		b2cReq.setMerId(MER_ID);
		String tranData = XmlUtil.parseBean4Xml(b2cReq);
		String  merSignMsg = HmacSHASign.hmacSHASign(tranData, md5);
		String tranDataBase64 = Base64Util.getBase64(tranData);
		paramMap.put("interfaceName", "1.0");
		paramMap.put("tranDataBase64", tranDataBase64);
		paramMap.put("merSignMsg", merSignMsg);
		paramMap.put("merchantId", MER_ID);
		paramMap.put("bankId", "");
		orderService.updateOrderStatusAndPayStatus(b2cReq.getOrderNo(), OrderStatus.wait_pay.getIndex(), PaymentStatusEnum.unpaid.getIndex());
		return paramMap;
	}
	
	/**
	 * 微信支付回调通知 更新订单、支付状态
	 * @param tranData
	 * @throws Exception
	 */
	public void weixinPayNotifyInfo(String orderNo) throws Exception {
		logger.info("更新商品库存,订单状态,支付状态开始"+orderNo);
		//支付成功情况下修改订单状态以及商品库存
		orderService.updateOrderStatusAndPayStatus(orderNo, OrderStatus.paid_shipped.getIndex(),PaymentStatusEnum.paid.getIndex()); 
	}
	
	/**
	 * 保存微信支付详情
	 * @param weixinPayResDto
	 * @throws Exception
	 */
	public void savePayInfo(WeixinPayResDto weixinPayResDto) throws Exception {
		//保存微信支付详情
		paydao.savePayInfo(weixinPayResDto); 
	}

}
