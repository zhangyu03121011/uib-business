package com.uib.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.uib.mobile.dto.RecommendProductLog;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.ptyt.entity.MemRankDto;
import com.uib.ptyt.service.MemMemberService;
import com.uib.ptyt.service.MemRankService;

/**
 * 分销商佣金结算job 被分享的商品已购买，订单已完成7天，给分享人派佣金
 * 
 * @author chengw
 *
 */
@Component
public class SettleCommissionTask {

	private Logger logger = LoggerFactory.getLogger(SettleCommissionTask.class);

	@Autowired
	private RecommendProductLogService recommendProductLogService;
	@Autowired
	private MemRankService memRankService;
	@Autowired
	private MemMemberService memMemberService;

	public void settleCommission() {
		try {
			logger.info("#=====分销商佣金结算开始...");
			// 查询已完成订单，并且是分享记录表未完成的数据
			List<RecommendProductLog> settleList = recommendProductLogService.querySettleRecords();
			Set<String> dateSet = new HashSet<String>();
			for (RecommendProductLog item : settleList) {
				// 订单支付完成时间
				dateSet.add(item.getPaymentDate());
			}

			for (String date : dateSet) {
				logger.info("#=====开始处理#date=" + date + "#的数据" + "=====#");
				this.processByDate(date, settleList);
				logger.info("#=====结束处理#date=" + date + "#的数据" + "=====#");
			}
			logger.info("#=====此次定时调度job实际处理数据量为：" + settleList.size());

			logger.info("#=====佣金结算完成...");

			logger.info("开始贡献值结算");

			List<RecommendProductLog> orderSettleList = recommendProductLogService.queryOrderSettleRecords();

			Set<String> orderDateSet = new HashSet<String>();
			for (RecommendProductLog item : orderSettleList) {
				// 订单支付完成时间
				orderDateSet.add(item.getPaymentDate());
			}

			for (String date : orderDateSet) {
				logger.info("#=====开始处理#date=" + date + "#的数据" + "=====#");
				this.processOrderByDate(date, orderSettleList);
				logger.info("#=====结束处理#date=" + date + "#的数据" + "=====#");
			}

			logger.info("贡献值结算结束");

			logger.info("#=====分销商佣金结算结束...");

		} catch (Exception e) {
			logger.error("#=====分销商佣金结算异常", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, timeout = 10, rollbackFor = Exception.class)
	private void processByDate(String date, List<RecommendProductLog> settleList) throws Exception {
		List<RecommendProductLog> currentSettleList = new ArrayList<RecommendProductLog>();
		for (RecommendProductLog item : settleList) {
			if (date.equals(item.getPaymentDate())) {
				currentSettleList.add(this.computeCommission(item));
			}
		}

		logger.info("开始计算推广记录数={}", currentSettleList.size());

		if (!currentSettleList.isEmpty()) {
			// 批量更新推荐人的佣金和贡献值
			recommendProductLogService.batchSettleCommission(currentSettleList);
			// 更新被推荐人的佣金和贡献值
			recommendProductLogService.batchMemSettleCommission(currentSettleList);
			
			// 批量更新商品推荐记录明细表中的“是否结算字段”为已结算,同时更新佣金字段
			recommendProductLogService.batchUpdateIsSettlement(currentSettleList);
			// 更新订单项表结算字段为已结算
			recommendProductLogService.batchUpdateOrderSettlement(currentSettleList);

			// 批量更新推荐人等级
			batchRecommendMemberRankId(currentSettleList);
			// 批量更新被推荐人等级
			batchMemberRankId(currentSettleList);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, timeout = 10, rollbackFor = Exception.class)
	private void processOrderByDate(String date, List<RecommendProductLog> settleList) throws Exception {
		List<RecommendProductLog> currentSettleList = new ArrayList<RecommendProductLog>();
		for (RecommendProductLog item : settleList) {
			if (date.equals(item.getPaymentDate())) {
				RecommendProductLog recommendProductLog = this.computeCommission(item);
				recommendProductLog.setRecommendMemberId(item.getMemberId());
				currentSettleList.add(recommendProductLog);
			}
		}

		logger.info("开始计算推广记录数={}", currentSettleList.size());

		if (!currentSettleList.isEmpty()) {

			// 批量更新商品推荐记录明细表中的“是否结算字段”为已结算
			recommendProductLogService.batchUpdateOrderSettlement(currentSettleList);

			// 更新普通用户的贡献值
			recommendProductLogService.batchMemSettleCommission(currentSettleList);

			// 批量更新推荐人等级
			batchRecommendMemberRankId(currentSettleList);
			// 批量更新被推荐人等级
			batchMemberRankId(currentSettleList);
		}

	}

	/**
	 * 计算佣金
	 * 
	 * @param item
	 * @return
	 * @throws Exception
	 */
	private RecommendProductLog computeCommission(RecommendProductLog item) throws Exception {
		RecommendProductLog currentSettle = new RecommendProductLog();
		//拿到被推荐人的用户角色类型
		Map<String,Object> member= memMemberService.queryMemMember(null,item.getMemberId().toString());
		String userType  = member.get("userType").toString();
		int quantity = item.getQuantity();
		Double sellPrice = item.getSellPrice();
		Double supplyPrice = item.getBSupplyPrice();
		//Double dcommission = (item.getSellPrice() - item.getBSupplyPrice()) / 2;
		Double dcommission = (quantity*(sellPrice-supplyPrice))/2;
		BigDecimal bcommission = new BigDecimal(dcommission);
		Double commission = bcommission.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		currentSettle.setCommission(commission);
		currentSettle.setId(item.getId());
		currentSettle.setRecommendMemberId(item.getRecommendMemberId());
		currentSettle.setMemberId(item.getMemberId());
		currentSettle.setOrderNo(item.getOrderNo());
		currentSettle.setUserType(userType);  //被推荐人用户角色
		currentSettle.setRecommendUserType(item.getRecommendUserType());//推荐人用户角色
		return currentSettle;
	}

	/*
	 * 批量更新推荐者的等级（rankid）状态
	 */
	private void batchRecommendMemberRankId(List<RecommendProductLog> currentSettleList) throws Exception {
		
		logger.info("更新推荐者的等级开始==================");
		// 拿到当前的推荐人的佣金信息
		List<Map<String, Object>> memberList = memMemberService.getRecommendMemberByIds(currentSettleList);

		// * 批量更新用户表中的等级（rankid）状态
		List<MemRankDto> rankList = memRankService.queryMemRank();
		// 用户表里面的佣金
		for (int i = 0; i < memberList.size(); i++) {
			Map<String, Object> member = memberList.get(i);
			String RecommendUserType =member.get("userType").toString();
			Double commission = Double.parseDouble(member.get("sumamount").toString());
			//推荐人只用当时B端用户端时候，才有贡献值，C端没有，没有贡献值的不用更新等级
			if(RecommendUserType.equals("2")){
				for (int j = 0; j < rankList.size(); j++) {
					MemRankDto memRank = rankList.get(j);
					Double amount = memRank.getAmount();
					if (commission <= amount) {
						member.put("rankId", rankList.get(j - 1).getId());
						break;
					}
				}
			}
		}
		//更新推荐人并且这个推荐人是B端，更新他的等级
		recommendProductLogService.batchUpdateMemberRank(memberList);
		for (Map<String, Object> member : memberList) {
			logger.info("member id={},rankId={}", member.get("id"), member.get("rankId"));
		}
		logger.info("更新推荐者的等级结束==================");
	}
	
	
	/*
	 * 批量更新被推荐者的等级（rankid）状态
	 */
	private void batchMemberRankId(List<RecommendProductLog> currentSettleList) throws Exception {
		logger.info("更新被推荐者的等级开始==================");
		// 拿到当前的被推荐人的佣金信息
		List<Map<String, Object>> memberList = memMemberService.getMemberByIds(currentSettleList);

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
		logger.info("更新被推荐者的等级结束==================");
	}
}
