package com.uib.quartz;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.mobile.dto.RecommendProductLog;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.order.service.OrderCommissionSettleLogService;

/**
 * 扫描订单佣金结算记录表，把状态为0的订单号下面的商品更新到商品推荐记录表，商品推荐记录表状态变为1未结算
 * @author chengw
 *
 */
@Component
public class OrderCommissionSettleTask {
	
	private Logger logger = LoggerFactory.getLogger(OrderCommissionSettleTask.class);
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	@Autowired
	private OrderCommissionSettleLogService orderCommissionSettleLogService;
	
	public void orderCommissionSettle(){
		try {
			logger.info("=======改变推荐商品状态开始=========");
			
			//查找已完成的订单，查询此订单的商品和会员信息
			List<Map<String,Object>> parm = orderCommissionSettleLogService.queryOrderAndProductInfo();
			
			List<RecommendProductLog> settleList = null;
			if(!parm.isEmpty()){
				//通过商品id、会员id，查询商品推荐记录表是否有符合条件的记录，并查询推荐记录id
				logger.info("=======需要处理的订单记录数为：" + parm.size() + "=======");
				settleList = recommendProductLogService.queryId(parm);
			}else{
				logger.info("=======需要处理的订单记录数为：0=======");
			}
			if(!settleList.isEmpty()){
				logger.info("=======(商品推荐记录表)需要处理的记录数为：" + settleList.size() + "=========");
				//商品推荐记录表状态由0变为1，并且更新订单号
				recommendProductLogService.batchUpdateIsNotSettlement(settleList);
			}else{
				logger.info("=======(商品推荐记录表)需要处理的记录数为："+ settleList.size() +"=========");
			}
			
			if(!parm.isEmpty()){
				//订单佣金结算记录表状态置为1（已处理）
				orderCommissionSettleLogService.batchUpdateIsRecorded(parm);
			}
			
			logger.info("=======改变推荐商品状态结束=========");
		} catch (Exception e) {
			logger.error("改变推荐商品状态异常：" + e);
		}
	}

}
