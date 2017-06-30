package com.uib.ptyt.dao;

import java.util.List;
import java.util.Map;

import com.uib.common.annotation.MyBatisDao;
import com.uib.member.entity.MemMember;
import com.uib.mobile.dto.RecommendProductLog;
import com.uib.order.entity.OrderTable;

@MyBatisDao
public interface MemMemberDao {

	/**
	 * 根据openId查询会员信息
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> queryMemMember(Map<String,Object> map) throws Exception;
	
	/**
	 * 根据id更新会员信息
	 * @param id
	 * @throws Exception
	 */
	public void updateMemMember(Map<String,Object> map) throws Exception;
	/**
	 * 根据id更新会员手机
	 * @param map
	 * @throws Exception
	 */
	public void updatePhone(Map<String,Object> map) throws Exception;
	
	
	/**
	 * 关注微信生产一个用户
	 * @throws Exception
	 */
	public void createUser(MemMember member) throws Exception;
	
	/**
	 * 根据openId查询会员id
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object>  getuserIdByopenId(String openId) throws Exception;
	
	/**
	 * 根据userId查询商户Id
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public String getmerchartIdByUserId(String userId) throws Exception;
	
	
	/**
	 * 根据openId更新用户信息头像，昵称，性别
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public void updateUserHead(MemMember member) throws Exception;
	
	public void updateUserName(MemMember member) throws Exception;
	
	
	public List<Map<String,Object>> getMemberByIds(List<RecommendProductLog> settleList) throws Exception;
	
	public List<Map<String,Object>> getRecommendMemberByIds(List<RecommendProductLog> settleList) throws Exception;
	
	public List<Map<String,Object>> getMemByUsernameList(List<OrderTable> orderList) throws Exception;
	
	public int getCode() throws Exception;
	
	public void updateCode(int code) throws Exception;
}
