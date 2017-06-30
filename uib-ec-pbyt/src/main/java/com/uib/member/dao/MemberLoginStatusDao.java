/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.member.dao;

import com.uib.member.entity.MemberLoginStatus;

/**
 * @ClassName: MemberLoginStatusDao
 * @author sl
 * @date 2015年9月24日 下午5:25:28
 */
public interface MemberLoginStatusDao {
	/**
	 * 
	 * 查询sessionId 是否存在
	 */
	public Integer findExistsBySessionId(String sessionId);

	/**
	 * 保存手机登陆记录
	 * 
	 * @param memberLoginStatus
	 * @throws Exception
	 */
	void saveMemberLoginStatus(MemberLoginStatus memberLoginStatus) throws Exception;
	
	/**
	 * 根据用户ID查询手机APP登陆状态
	 * @param MemberId
	 * @return
	 * @throws Exception
	 */
	MemberLoginStatus findByMemberId(String MemberId)  throws Exception;
	
	/**
	 * 根据用户ID以及sessionId查询手机App登陆装备
	 * @param MemberId
	 * @param sessionId
	 * @return
	 * @throws Exception
	 */
	MemberLoginStatus findByMemberIdAndSessionId(String memberId, String sessionId) throws Exception;
}
