package com.uib.mobile.service;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.RecommendProductLog;

public interface RecommendProductLogService {
	/**
	 * 添加商品推荐记录
	 * 
	 * @param memberId
	 * @param rMemberId
	 * @param productId
	 * @throws Exception
	 */
	public void addRecommendProductLog(String memberId, String rMemberId, String productId, String recommendId, String cartItemId,String recommendUserType) throws Exception;

	public Integer checkOnly(String memberId, String rMemberId, String productId) throws Exception;

	/**
	 * 佣金结算
	 * 
	 * @param number
	 * @param memberId
	 * @param productId
	 * @throws Exception
	 */
	// public void commissionSettlement(Integer number, String memberId, String
	// productId,String orderNo);

	/**
	 * 查询符合结算佣金条件的推荐记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RecommendProductLog> querySettleRecords() throws Exception;

	/**
	 * 查询订单结算条件
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RecommendProductLog> queryOrderSettleRecords() throws Exception;

	/**
	 * 批量给会员结算佣金
	 * 
	 * @return
	 * @throws Exception
	 */
	public void batchSettleCommission(List<RecommendProductLog> settleList) throws Exception;

	/**
	 * 更新会员的贡献值
	 * 
	 * @param settleList
	 * @throws Exception
	 */
	void batchMemSettleCommission(List<RecommendProductLog> settleList) throws Exception;

	/**
	 * 批量设置推荐记录表记录设置为已结算
	 * 
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateIsSettlement(List<RecommendProductLog> settleList) throws Exception;

	/**
	 * 
	 * @param settleList
	 * @throws Exception
	 */
	public void batchUpdateOrderSettlement(List<RecommendProductLog> settleList) throws Exception;

	/**
	 * 批量更新被推荐人等级
	 * 
	 * @param rankList
	 * @throws Exception
	 */
	public void batchUpdateMemberRank(List<Map<String, Object>> rankList) throws Exception;
	
	
	public void updateIsSettlement(String orderNo, String cartItemId) throws Exception;

	/**
	 * 通过商品id和被推荐人会员id，查找商品推荐记录表id
	 * 
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public List<RecommendProductLog> queryId(List<Map<String, Object>> parm) throws Exception;

	/**
	 * 批量设置推荐记录表记录设置为未结算
	 * 
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateIsNotSettlement(List<RecommendProductLog> settleList) throws Exception;

	/**
	 * 查询该用户下所有的推荐记录(可分页)
	 * 
	 * @param recommendMemberId
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryRecommendDetail(String memberId, String page) throws Exception;
}
