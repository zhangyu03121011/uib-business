package com.uib.member.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.common.utils.UUIDGenerator;
import com.easypay.core.dao.MyBatisDao;
import com.easypay.core.exception.GenericException;
import com.uib.member.dao.MemMemberDao;
import com.uib.member.entity.MemMember;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;

@Component
public class MemMemberDaoImpl extends MyBatisDao<MemMember> implements
		MemMemberDao {
	
	@Override
	public MemMember getMemMemberByUsername(String username)
			throws GenericException {
		return this.getUnique("getMemMemberByUsername", username);
	}

	@Override
	public MemMember getMemMemberByUsernameNew(String username) throws GenericException {
		return  this.getUnique("getMemMemberByUsernameNew", username);
		//return super.getMap("getMemMemberByUsernameNew", username);
	}

	@Override
	public void saveMember(MemMember member) throws Exception {
		this.save("saveMember", member);
	}

	@Override
	public MemMember getMemMember(String id) {
		return this.getUnique("get", id);
	}

	@Override
	public void updateById(Map<String, String> param) {
		super.update("updateById", param);

	}

	public Map<String, Object> findApproveInfo(String id) {
		return super.getMap("findApproveInfo", id);
	}
	
	@Override
	public Map<String, Object> getMemberbyCardId(String idCard) {
		return super.getMap("getMemberbyCardId", idCard);
	}

	@Override
	public MemMember memberLogin(String username, String phone)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", username);
		map.put("phone", phone);
		return this.getUnique("memberLogin", map);
	}

	@Override
	public MemMember getMemberByPhone(String phone) throws Exception {
		return this.getUnique("getMemberByPhone", phone);
	}

	@Override
	public void updatePassword(Map<String, Object> paramMap) throws Exception {
		this.update("updatePassword", paramMap);
	}

	@Override
	public MemMember getApproveByUserName(String userName) throws Exception {
		return this.getUnique("getApproveByUserName", userName);
	}
	
	@Override
	public MemMember queryIsAuthByUserName(String userId,String authFlag) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("authFlag", authFlag);
		return this.getUnique("queryIsAuthByUserName", map);
	}

	@Override
	public int verifyPassword(String userName, String password) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("password", password);
		return (int) this.getObjectValue("verifyPassword", map);
	}
	
	/**
	 * 根据sessionId查询会员信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public MemMember getMemMemberBySessionId(String sessionId) {
		return this.getUnique("getMemMemberBySessionId", sessionId);
	}
	
	/**
	 * 保存会员第三方授权登录信息
	 * 
	 * @param member
	 * @throws Exception
	 */
	public void saveMemberPreAuthInfo(MemMember memMember) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", UUIDGenerator.getUUID());
		map.put("memmemberId", memMember.getId());
		map.put("authFlag", memMember.getAuthFlag());
		this.save("saveMemberPreAuthInfo", map);
	}

	@Override
	public void updateSessionId(String userName,String sessionId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("phone", userName);
		map.put("sessionId", sessionId);
		this.update("updateSessionId", map);
		
	}

	@Override
	public void updateCommission(String memMemberId, String commission)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memMemberId", memMemberId);
		map.put("commission", commission);
		this.update("updateCommission", map);
		
	}

	@Override
	public void updatePayPassword(String id, String payPassword)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("payPassword", payPassword);
		this.update("updatePayPassword", map);
		
	}

	@Override
	public void updateMemberInfoById(Map<String, String> param) throws Exception {
		this.update("updateMemberInfoById", param);
		
	}

	@Override
	public Object queryMemberByIdCard(String idCard) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("idCard", idCard);
		map.put("userId", (String)UserSession.getSession(WxConstant.wx_user_id));
		return this.getObjectValue("queryMemberByIdCard",map);
	}

	@Override
	public int queryMemberInfo(String userName, String password)throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("password", password);
		return (int) this.getObjectValue("queryMemberInfo",map);
	}
	
}
