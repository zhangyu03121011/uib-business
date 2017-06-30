package com.uib.mobile.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	
//	private Logger logger = LoggerFactory.getLogger(RecommendProductLogServiceImpl.class);

	
	@Autowired
	private RecommendProductLogDao recommendProductLogDao;
	@Autowired
	private ProductService productService;
	@Autowired
	private MemMemberService memMemberService;

	@Override
	public void addRecommendProductLog(String memberId, String rMemberId, String productId) throws Exception {
		if (Utils.isObjectsBlank(memberId, rMemberId, productId)) {
			throw new Exception("memberId、rMemberId、productId参数某些为空");
		}
		recommendProductLogDao.save(constructRecommendProductLog(memberId, rMemberId, productId));
	}

	public RecommendProductLog constructRecommendProductLog(String memberId, String rMemberId, String productId) {
		RecommendProductLog log = new RecommendProductLog();
		log.setId(UUIDGenerator.getUUID());
		log.setMemberId(memberId);
		log.setRecommendMemberId(rMemberId);
		log.setProductId(productId);
		log.setCreateTime(new Date());
		log.setDelFlag('0');
		return log;
	}

	@Override
	public Integer checkOnly(String memberId, String rMemberId, String productId)
			throws Exception {
		if (Utils.isObjectsBlank(memberId, rMemberId, productId)) {
			throw new Exception("memberId、rMemberId、productId参数某些为空");
		}		
		return recommendProductLogDao.checkOnly(memberId, rMemberId, productId);
	}

//	@Override
//	public void commissionSettlement(Integer number, String memberId,String productId,String orderNo){
//		try {
//			logger.info("佣金结算入参number=" + number + ",memberId=" + memberId + ",productId=" + productId+ ",orderNo=" + orderNo);
//			if (Utils.isObjectsBlank(number, memberId, productId,orderNo)) {
//				logger.info("number, memberId, productId,orderNo参数某些为空");
//				return;
//			}
//			// 1、查找推荐人Id(List)
//			List<Map<String, Object>>  rMemberIds =new ArrayList<Map<String,Object>>();
//			rMemberIds = recommendProductLogDao.queryrMemberId(number, memberId, productId);
//			if(null == rMemberIds || rMemberIds.size()==0){
//				logger.info("没有找到推荐会员【" + memberId + "】");
//				return;
//			}
//			// 2、计算该商品的佣金
//			Product product = productService.queryProductByProductId(productId);
//			if(null == product){
//				logger.info("没有找到推荐商品【" + productId + "】");
//				return;
//			}
//			double commission =(product.getPrice()*product.getCommPercent())/100;
//			// 3、将佣金结算给推荐人
//			for(Map<String, Object> merberIdMap : rMemberIds){	
//				String rMemberId = String.valueOf(merberIdMap.get("id"));
//				memMemberService.updateCommission(rMemberId, String.valueOf(commission));
//				//4、更新推荐人信息表的状态为未结算
//				recommendProductLogDao.updateIsSettlement(memberId, rMemberId, productId,orderNo);
//			}
//		} catch (Exception e) {
//			logger.info("佣金结算异常：" + e);
//		}		
//	}

	@Override
	public List<RecommendProductLog> querySettleRecords() throws Exception {
		return recommendProductLogDao.querySettleRecords();
	}

	@Override
	public void batchSettleCommission(List<RecommendProductLog> settleList)
			throws Exception {
		recommendProductLogDao.batchSettleCommission(settleList);
		
	}

	@Override
	public void batchUpdateIsSettlement(List<RecommendProductLog> settleList)
			throws Exception {
		recommendProductLogDao.batchUpdateIsSettlement(settleList);
		
	}

	@Override
	public List<RecommendProductLog> queryId(List<Map<String, Object>> parm)
			throws Exception {
		return recommendProductLogDao.queryId(parm);
	}

	@Override
	public void batchUpdateIsNotSettlement(List<RecommendProductLog> settleList)
			throws Exception {
		recommendProductLogDao.batchUpdateIsNotSettlement(settleList);
	}

}
