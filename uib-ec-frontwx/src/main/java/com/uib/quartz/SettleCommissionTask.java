package com.uib.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uib.mobile.dto.RecommendProductLog;
import com.uib.mobile.service.RecommendProductLogService;

/**
 * 佣金结算job
 * 被分享的商品已购买，订单已完成15天，给分享人派佣金
 * @author chengw
 *
 */
@Component
public class SettleCommissionTask {
	
	private Logger logger = LoggerFactory.getLogger(SettleCommissionTask.class);
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	public void settleCommission(){
		try {
			logger.info("#=====佣金结算开始...");
			List<RecommendProductLog> settleList = recommendProductLogService.querySettleRecords();
			Set<String> dateSet = new HashSet<String>();
			for (RecommendProductLog item : settleList) {
				dateSet.add(item.getPaymentDate());
			}
			
			for (String date : dateSet) {
				logger.info("#=====开始处理#date=" + date + "#的数据" + "=====#");
				this.processByDate(date, settleList);
				logger.info("#=====结束处理#date=" + date + "#的数据" + "=====#");
			}
			logger.info("#=====此次定时调度job实际处理数据量为：" + settleList.size());
			
		} catch (Exception e) {
			logger.error("#=====佣金结算异常",e);
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, timeout = 10,rollbackFor=Exception.class)
	private void processByDate(String date,List<RecommendProductLog> settleList) throws Exception{
		List<RecommendProductLog> currentSettleList = new ArrayList<RecommendProductLog>();
		for (RecommendProductLog item : settleList) {
			if(date.equals(item.getPaymentDate())){
				currentSettleList.add(this.computeCommission(item));
			}
		}
		if(!currentSettleList.isEmpty()){
			recommendProductLogService.batchSettleCommission(currentSettleList);
			recommendProductLogService.batchUpdateIsSettlement(currentSettleList);
		}
		
	}
	
	/**
	 * 计算佣金
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private RecommendProductLog computeCommission(RecommendProductLog item) throws Exception{
		RecommendProductLog currentSettle = new RecommendProductLog();
		Double dcommission = item.getPrice() * item.getCommPercent() / 100 ;
		BigDecimal bcommission = new BigDecimal(dcommission);
		Double commission = bcommission.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		currentSettle.setCommission(commission);
		currentSettle.setId(item.getId());
		currentSettle.setRecommendMemberId(item.getRecommendMemberId());
		return currentSettle;
	}

}
