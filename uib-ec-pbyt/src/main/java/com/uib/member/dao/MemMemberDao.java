/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.dao;

import java.util.Map;

import com.easypay.core.exception.GenericException;
import com.uib.member.entity.MemMember;

/**
 * 会员表DAO接口
 * 
 * @author kevin
 * @version 2015-05-31
 */
public interface MemMemberDao {

	/**
	 * 根据用户名查询会员信息
	 * 
	 * @param username
	 * @return
	 * @throws GenericException
	 */
	MemMember getMemMemberByUsername(String username);

	
	MemMember getMemMemberByUsernameNew(String username);

	/**
	 * 根据编号查询会员信息
	 * 
	 * @param username
	 * @return
	 * @throws GenericException
	 */
	MemMember getMemMember(String id);

	/**
	 * 根据用户名或手机号查询登陆用户
	 * 
	 * @param username
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	MemMember memberLogin(String username, String phone) throws Exception;

	/**
	 * 保存会员信息
	 * 
	 * @param member
	 * @throws Exception
	 */
	void saveMember(MemMember member) throws Exception;

	public void updateById(Map<String, String> param);

	public Map<String, Object> findApproveInfo(String id);
	
	public Map<String, Object> getMemberbyCardId(String idCard);

	/**
	 * 根据手机号获取用户信息
	 * 
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	MemMember getMemberByPhone(String phone) throws Exception;

	/**
	 * 根据用户名修改密码
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	void updatePassword(Map<String, Object> paramMap) throws Exception;

	/**
	 * 查询身份认证信息
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	MemMember getApproveByUserName(String userName) throws Exception;
	
	/**
	 * 查询当前用户是否预授权
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	MemMember queryIsAuthByUserName(String userId,String authFlag) throws Exception;

	int verifyPassword(String userName, String password);
	
	/**
	 * 根据sessionId查询会员信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public MemMember getMemMemberBySessionId(String sessionId) throws Exception;
	
	/**
	 * 保存会员第三方授权登录信息
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void saveMemberPreAuthInfo(MemMember memMember) throws Exception;
	/**
	 * 更新sessionid
	 * @param userName
	 * @param sessionId
	 * @throws Exception
	 */
	public void updateSessionId(String userName,String sessionId)throws Exception;
	
	public void updateCommission(String memMemberId,String commission) throws Exception;
	/**
	 * 设置/忘记支付密码
	 * @param id
	 * @param payPassword
	 * @throws Exception
	 */
	public void updatePayPassword(String id,String payPassword) throws Exception;
	
	/**
	 * 通过会员id更新会员信息
	 * @param memberId
	 * @throws Exception
	 */
	public void updateMemberInfoById(Map<String, String> param) throws Exception;
	/**
	 * 查询会员ID是否唯一
	 * @param idCard
	 * @return
	 * @throws Exception
	 */
	public Object queryMemberByIdCard(String idCard)throws Exception;
	
	/**
	 * 根据用户名和登陆密码查询用户信息
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int queryMemberInfo(String userName ,String password)throws Exception;
}