package com.uib.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.mobile.service.RecommendProductLogService;
import com.uib.order.entity.OrderTable;
import com.uib.order.service.OrderCommissionSettleLogService;
import com.uib.ptyt.entity.MemRankDto;
import com.uib.ptyt.service.MemMemberService;
import com.uib.ptyt.service.MemRankService;

/**
 * 扫描订单表里订单类型=0（平台），把订单类型状态为0的，并且佣金未结算的数据，更新订单表里面的佣金字段，同时更新用户表的佣金字段
 * @author chengjian
 * datetime 2016-08-23
 *
 */

@Component
public class CustomerCommissionSettleTask {
	
	private Logger logger = LoggerFactory.getLogger(CustomerCommissionSettleTask.class);
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private MemRankService memRankService;
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	@Autowired
	private OrderCommissionSettleLogService orderCommissionSettleLogService;
	
	public void customerCommission(){
		try {
			logger.info("=======C端客户从平台结算佣金状态开始=========");
			
			//查找已完成的订单，查询此未结算订单的商品和会员信息
			List<Map<String,Object>> parm = orderCommissionSettleLogService.getOrderAndProdInfo();
			List<OrderTable> orderList = new ArrayList<OrderTable>();
			if(!parm.isEmpty()){
				logger.info("=======需要处理的订单记录数为：" + parm.size() + "=======");
				for(Map<String,Object> item:parm){
					OrderTable orderTable = this.computeCommission(item);
					orderList.add(orderTable);
				}
			}else{
				logger.info("=======需要处理的订单记录数为：0=======");
				return ;
			}
			if(!orderList.isEmpty()){
				logger.info("=======C端计算佣金订单表需要处理的记录数为：" + orderList.size() + "=========");
				//批量更新订单表里面的佣金字段
				orderCommissionSettleLogService.batchUpdateOrderCommssion(orderList);
				//在更新订单项里面：未结算字段=结算
				orderCommissionSettleLogService.batchUpdateOrderIsNotSettlement(orderList);
				// 批量更新普通用户的贡献值和商户的（贡献值和佣金）
				orderCommissionSettleLogService.batchUpdateMemberSumAmount(orderList);
				//更新普通等级rankId用户
				batchMemberRankId(orderList);
			}else{
				logger.info("=======C端计算佣金订单表需要处理的记录数为：0=========");
				return;
			}
			
			
			logger.info("=======C端客户从平台结算佣金状态结束=========");
		} catch (Exception e) {
			logger.error("改变推荐商品状态异常：" + e);
		}
	}
	
	/**
	 * 计算佣金
	 * 
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private OrderTable computeCommission(Map<String,Object> item) throws Exception {
		OrderTable orderTable = new OrderTable();
		int number = Integer.parseInt(item.get("number").toString());  //数量
		Double sellPrice = Double.parseDouble(item.get("sellPrice").toString());   //售价
		Double BSupplyPrice = Double.parseDouble(item.get("BSupplyPrice").toString());  //平台供货价
		Double dcommission = (number*(sellPrice-BSupplyPrice))/ 2;
		BigDecimal bcommission = new BigDecimal(dcommission);
		Double commission = bcommission.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		orderTable.setCommission(commission);
		orderTable.setOrderNo(item.get("orderNo").toString());
		orderTable.setUserName(item.get("userName").toString());
		orderTable.setUserType(item.get("userType").toString());
		orderTable.setMemberId(item.get("memberId").toString());
		return orderTable;
	}
	
	
	/*
	 * 批量更新用户表中的等级（rankid）状态
	 */
	private void batchMemberRankId(List<OrderTable> orderList) throws Exception {
		// 拿到当前用户的贡献值
		List<Map<String, Object>> memberList = memMemberService.getMemByUsernameList(orderList);

		// * 批量更新用户表中的等级（rankid）状态
		List<MemRankDto> rankList = memRankService.queryMemRank();
		// 用户表里面的佣金
		for (int i = 0; i < memberList.size(); i++) {
			Map<String, Object> member = memberList.get(i);
			Double commission = Double.parseDouble(member.get("sumamount").toString());
			for (int j = 0; j < rankList.size(); j++) {
				MemRankDto memRank = rankList.get(j);
				Double amount = memRank.getAmount();
				if (commission <= amount) {
					member.put("rankId", rankList.get(j - 1).getId());
					break;
				}
			}
		}

		recommendProductLogService.batchUpdateMemberRank(memberList);

		for (Map<String, Object> member : memberList) {
			logger.info("member id={},rankId={}", member.get("id"), member.get("rankId"));
		}

	}

}
