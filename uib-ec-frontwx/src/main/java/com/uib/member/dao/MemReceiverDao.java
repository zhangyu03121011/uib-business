/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.dao;


import java.util.List;

import com.uib.member.entity.MemReceiver;



/**
 * 收货地址表DAO接口
 */
public interface MemReceiverDao  {
	
	/**
	 * 根据会员统计地址数
	 * @param memberId
	 * @return
	 * @throws GenericException
	 */
	Integer getMemReceiverByAddrCount(String memberId);
	

	/**
	 * 根据会员统计地址数
	 * @param memberId
	 * @return 
	 * @throws Exception
	 */
	List<MemReceiver> getMemReceiverByAddress(String memberId) throws Exception;
	
	/**
	 * 根据id统计地址数
	 * @param id
	 * @return MemReceiver
	 * @throws Exception
	 */
	MemReceiver getMemReceiverById (String id) throws Exception;
	
	/**
	 * 根据会员统计地址数
	 * @param memReceiver
	 * @return
	 * @throws Exception
	 */
	void update(MemReceiver memReceiver) throws Exception;
	
	/**
	 * 根据id删除地址
	 * @param id
	 * @return
	 * @throws Exception
	 */
	void delete(String id) throws Exception;
	
	/**
	 * 保存收货地址地址
	 * @param memReceiver
	 * @return
	 * @throws Exception
	 */
	void save(MemReceiver memReceiver) throws Exception;
	
	/**
	 * 修改默认地址
	 * @param memReceiver
	 * @return
	 * @throws Exception
	 */
	void updateIsDefault(MemReceiver memReceiver) throws Exception;
	
	/**
	 * 根据会员查询默认地址
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	MemReceiver getDefaultMemReceiverByMemberId(String memberId) throws Exception;
	
	/***
	 * 获取会员上次下单保存的地址
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	MemReceiver getLastOrderReceiverByMemberId(String memberId) throws Exception;
}