package com.uib.mobile.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.mobile.dao.RecommendProductLogDao;
import com.uib.mobile.dto.RecommendProductLog;

@Component
public class RecommendProductLogDaoImpl extends MyBatisDao<RecommendProductLog> implements RecommendProductLogDao {

	@Override
	public void save(RecommendProductLog log) throws Exception {
		this.save("insert", log);
	}

	@Override
	public Integer checkOnly(String memberId, String rMemberId, String productId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("rMemberId", rMemberId);
		map.put("productId", productId);
		return (Integer) this.getObjectValue("checkOnly", map);
	}

	@Override
	public void updateIsSettlement(String orderNo, String cartItemId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cartItemId", cartItemId);
		map.put("orderNo", orderNo);
		this.update("updateIsSettlement", map);

	}

	@Override
	public List<Map<String, Object>> queryrMemberId(Integer number, String memberId, String productId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("number", number);
		map.put("memberId", memberId);
		map.put("productId", productId);
		return this.getList("queryrMemberId", map);
	}

	@Override
	public List<RecommendProductLog> querySettleRecords() throws Exception {
		return this.getObjectList("querySettleRecords");
	}

	@Override
	public List<RecommendProductLog> queryOrderSettleRecords() throws Exception {
		return this.getObjectList("queryOrderSettleRecords");
	}

	@Override
	public void batchSettleCommission(List<RecommendProductLog> settleList) throws Exception {
		this.update("batchSettleCommission", settleList);
	}

	@Override
	public void batchMemSettleCommission(List<RecommendProductLog> settleList) throws Exception {
		this.update("batchMemSettleCommission", settleList);
	}

	@Override
	public void batchUpdateIsSettlement(List<RecommendProductLog> settleList) throws Exception {
		this.update("batchUpdateIsSettlement", settleList);

	}

	@Override
	public void batchOrderUpdateIsSettlement(List<RecommendProductLog> settleList) throws Exception {
		this.update("batchOrderUpdateIsSettlement", settleList);

	}

	@Override
	public void batchUpdateMemberRank(List<Map<String, Object>> rankList) throws Exception {
		this.update("batchUpdateMemberRank", rankList);

	}

	@Override
	public void batchUpdateOrderSettlement(List<RecommendProductLog> rankList) throws Exception {
		this.update("batchUpdateOrderSettlement", rankList);

	}

	@Override
	public List<RecommendProductLog> queryId(List<Map<String, Object>> parm) throws Exception {
		return this.getObjectList("queryId", parm);
	}

	@Override
	public void batchUpdateIsNotSettlement(List<RecommendProductLog> settleList) throws Exception {
		this.update("batchUpdateIsNotSettlement", settleList);
	}

	@Override
	public List<Map<String,Object>> queryRecommendDetail(Map<String, Object> map) throws Exception {
		return this.getList("queryRecommendDetail", map);
	}

}
