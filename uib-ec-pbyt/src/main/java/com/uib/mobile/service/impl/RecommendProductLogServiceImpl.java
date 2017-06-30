package com.uib.mobile.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.common.utils.UUIDGenerator;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dao.RecommendProductLogDao;
import com.uib.mobile.dto.RecommendProductLog;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.product.service.ProductService;
import com.uib.serviceUtils.Utils;

@Service
public class RecommendProductLogServiceImpl implements RecommendProductLogService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RecommendProductLogDao recommendProductLogDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private MemMemberService memMemberService;

	@Override
	public void addRecommendProductLog(String memberId, String rMemberId, String productId, String recommendId, String cartItemId,String recommendUserType) throws Exception {
		if (Utils.isObjectsBlank(memberId, rMemberId, productId,recommendUserType)) {
			throw new Exception("memberId、rMemberId、productId参数某些为空");
		}
		recommendProductLogDao.save(constructRecommendProductLog(memberId, rMemberId, productId, recommendId, cartItemId,recommendUserType));
	}

	public RecommendProductLog constructRecommendProductLog(String memberId, String rMemberId, String productId, String recommendId, String cartItemId,String recommendUserType) {
		RecommendProductLog log = new RecommendProductLog();
		log.setId(UUIDGenerator.getUUID());
		log.setMemberId(memberId);
		log.setRecommendMemberId(rMemberId);
		log.setProductId(productId);
		log.setCreateTime(new Date());
		log.setDelFlag('0');
		log.setRecommendId(recommendId);
		log.setCartItemId(cartItemId);
		log.setRecommendUserType(recommendUserType);
		return log;
	}

	@Override
	public Integer checkOnly(String memberId, String rMemberId, String productId) throws Exception {
		if (Utils.isObjectsBlank(memberId, rMemberId, productId)) {
			throw new Exception("memberId、rMemberId、productId参数某些为空");
		}
		return recommendProductLogDao.checkOnly(memberId, rMemberId, productId);
	}

	// @Override
	// public void commissionSettlement(Integer number, String memberId,String
	// productId,String orderNo){
	// try {
	// logger.info("佣金结算入参number=" + number + ",memberId=" + memberId +
	// ",productId=" + productId+ ",orderNo=" + orderNo);
	// if (Utils.isObjectsBlank(number, memberId, productId,orderNo)) {
	// logger.info("number, memberId, productId,orderNo参数某些为空");
	// return;
	// }O
	// // 1、查找推荐人Id(List)
	// List<Map<String, Object>> rMemberIds =new
	// ArrayList<Map<String,Object>>();
	// rMemberIds = recommendProductLogDao.queryrMemberId(number, memberId,
	// productId);
	// if(null == rMemberIds || rMemberIds.size()==0){
	// logger.info("没有找到推荐会员【" + memberId + "】");
	// return;
	// }
	// // 2、计算该商品的佣金
	// Product product = productService.queryProductByProductId(productId);
	// if(null == product){
	// logger.info("没有找到推荐商品【" + productId + "】");
	// return;
	// }
	// double commission =(product.getPrice()*product.getCommPercent())/100;
	// // 3、将佣金结算给推荐人
	// for(Map<String, Object> merberIdMap : rMemberIds){
	// String rMemberId = String.valueOf(merberIdMap.get("id"));
	// memMemberService.updateCommission(rMemberId, String.valueOf(commission));
	// //4、更新推荐人信息表的状态为未结算
	// recommendProductLogDao.updateIsSettlement(memberId, rMemberId,
	// productId,orderNo);
	// }
	// } catch (Exception e) {
	// logger.info("佣金结算异常：" + e);
	// }
	// }

	@Override
	public List<RecommendProductLog> querySettleRecords() throws Exception {
		return recommendProductLogDao.querySettleRecords();
	}

	@Override
	public List<RecommendProductLog> queryOrderSettleRecords() throws Exception {
		return recommendProductLogDao.queryOrderSettleRecords();
	}

	@Override
	public void batchSettleCommission(List<RecommendProductLog> settleList) throws Exception {
		recommendProductLogDao.batchSettleCommission(settleList);

	}

	@Override
	public void batchMemSettleCommission(List<RecommendProductLog> settleList) throws Exception {
		logger.info(" 开始批量更新会员贡献值");

		recommendProductLogDao.batchMemSettleCommission(settleList);

		logger.info(" 完成批量更新会员贡献值");
	}

	@Override
	public void batchUpdateIsSettlement(List<RecommendProductLog> settleList) throws Exception {
		recommendProductLogDao.batchUpdateIsSettlement(settleList);

	}

	public void batchUpdateOrderSettlement(List<RecommendProductLog> settleList) throws Exception {
		recommendProductLogDao.batchUpdateOrderSettlement(settleList);
	}

	/**
	 * 批量更新被推荐用户等级
	 */
	@Override
	public void batchUpdateMemberRank(List<Map<String, Object>> rankList) throws Exception {
		recommendProductLogDao.batchUpdateMemberRank(rankList);
	}
	
	public void updateIsSettlement(String orderNo, String cartItemId) throws Exception {
		recommendProductLogDao.updateIsSettlement(orderNo, cartItemId);
	}

	@Override
	public List<RecommendProductLog> queryId(List<Map<String, Object>> parm) throws Exception {
		return recommendProductLogDao.queryId(parm);
	}

	@Override
	public void batchUpdateIsNotSettlement(List<RecommendProductLog> settleList) throws Exception {
		recommendProductLogDao.batchUpdateIsNotSettlement(settleList);
	}

	/**
	 * 查询该用户下所有的推荐记录(可分页)
	 * 
	 * @param recommendMemberId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryRecommendDetail(String memberId, String page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		if (null != page && "" != page) {
			int startSize = (Integer.valueOf(page) - 1) * 7;
			int pageSize = 7;
			map.put("startSize", startSize);
			map.put("pageSize", pageSize);
		}
		return recommendProductLogDao.queryRecommendDetail(map);
	}
}
