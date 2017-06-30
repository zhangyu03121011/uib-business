package com.uib.mobile.dao;

import java.util.List;
import java.util.Map;

import com.uib.mobile.dto.RecommendProductLog;

public interface RecommendProductLogDao {
	public void save(RecommendProductLog log) throws Exception;

	/**
	 * 检查商品分享（同一个人只能分享一次）
	 * 
	 * @param memberId
	 * @param rMemberId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public Integer checkOnly(String memberId, String rMemberId, String productId) throws Exception;

	/**
	 * 更新状态信息为未结算
	 * 
	 * @param memberId
	 * @param productId
	 * @throws Exception
	 */
	public void updateIsSettlement(String orderNo, String cartItemId) throws Exception;

	/**
	 * 查找推荐人id
	 * 
	 * @param memberId
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryrMemberId(Integer number, String memberId, String productId) throws Exception;

	/**
	 * 查询符合结算佣金条件的推荐记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RecommendProductLog> querySettleRecords() throws Exception;

	/**
	 * 查询符合结算佣金条件的推荐记录
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
	 * 批量给会员贡献值
	 * 
	 * @return
	 * @throws Exception
	 */
	public void batchMemSettleCommission(List<RecommendProductLog> settleList) throws Exception;

	/**
	 * 批量更新被推荐人等级
	 * 
	 * @param rankList
	 * @throws Exception
	 */
	public void batchUpdateMemberRank(List<Map<String, Object>> rankList) throws Exception;
	
	/**
	 * 对已结算订单更新状态
	 * 
	 * @param rankList
	 * @throws Exception
	 */
	public void batchUpdateOrderSettlement(List<RecommendProductLog> rankList) throws Exception;

	/**
	 * 批量设置推荐记录表记录设置为已结算
	 * 
	 * @return
	 * @throws Exception
	 */
	public void batchUpdateIsSettlement(List<RecommendProductLog> settleList) throws Exception;

	/**
	 * 批量设置推荐记录表记录设置为已结算
	 * 
	 * @return
	 * @throws Exception
	 */
	public void batchOrderUpdateIsSettlement(List<RecommendProductLog> settleList) throws Exception;

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
	 * 查询该用户下所有的推荐记录(可选择分页)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryRecommendDetail(Map<String, Object> map) throws Exception;
}
