package com.uib.weixin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uib.common.utils.DigestUtil;
import com.uib.common.utils.StringUtils;
import com.uib.common.utils.UUIDGenerator;
import com.uib.member.dao.MemMemberDao;
import com.uib.member.dto.MemberDto;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.service.MyCenterService;
import com.uib.weixin.util.UserSession;

@Service
public class MyCenterServiceImpl implements MyCenterService {
	private final Logger logger = Logger.getLogger(MyCenterServiceImpl.class);
	
	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private MemMemberDao memMemberDao;
	
	/**
	 * 保存用户信息
	 * @param toUserName
	 */
	public void weixinRegister(String fromUserName) {
		try {
			MemberDto memberDto = new MemberDto();
			memberDto.setWeixinName(fromUserName);
			memberDto.setUserName(fromUserName);
			memberDto.setPassword("123456");
			memberDto.setPhone("13111111111");
			
			MemMember member = new MemMember();
			BeanUtils.copyProperties(memberDto, member);
			member.setUsername(memberDto.getUserName());
			member.setId(UUIDGenerator.getUUID());
			String pwd = DigestUtil.MD5(member.getPassword());
			member.setPassword(pwd);
			//微信号
			member.setWeixinName(memberDto.getWeixinName());
			memMemberDao.saveMember(member);
			
			UserSession.setSession(WxConstant.wx_user_name, member.getUsername());
			UserSession.setSession(WxConstant.wx_user_id, member.getId());
		} catch (Exception e) {
			logger.info("保存用户信息异常======"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消关注保存用户信息
	 * @param toUserName
	 */
	public void unSubscribe(String fromUserName) {
		try {
			String id = (String)UserSession.getSession(WxConstant.wx_user_id);
			if(StringUtils.isNotEmpty(id)) {
				memMemberService.delete(id);
				UserSession.setSession(WxConstant.wx_user_name, null);
				UserSession.setSession(WxConstant.wx_user_id, null);
			}
		} catch (Exception e) {
			logger.info("保存用户信息异常======"+e);
			e.printStackTrace();
		}

	}
}
