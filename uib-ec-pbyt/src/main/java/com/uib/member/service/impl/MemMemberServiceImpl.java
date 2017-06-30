package com.uib.member.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.uib.common.Principal;
import com.uib.common.bean.MailBean;
import com.uib.common.enums.PresentType;
import com.uib.common.utils.DigestUtil;
import com.uib.common.utils.MailSenderUtil;
import com.uib.common.utils.PhoneMessageUtil;
import com.uib.common.utils.UUIDGenerator;
import com.uib.core.exception.GenericException;
import com.uib.member.dao.AreaDao;
import com.uib.member.dao.CouponCodeDao;
import com.uib.member.dao.CouponDao;
import com.uib.member.dao.DepositDao;
import com.uib.member.dao.MemMemberDao;
import com.uib.member.dao.MemReceiverDao;
import com.uib.member.dao.MemberLoginStatusDao;
import com.uib.member.dto.MemberDto;
import com.uib.member.entity.Area;
import com.uib.member.entity.Coupon;
import com.uib.member.entity.CouponCode;
import com.uib.member.entity.Deposit;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemReceiver;
import com.uib.member.entity.MemberLoginStatus;
import com.uib.member.service.MemMemberService;
import com.uib.order.dao.OrderDao;
import com.uib.order.entity.OrderTable;
import com.uib.weixin.util.UserSession;

@Service
@Transactional(readOnly = true)
public class MemMemberServiceImpl implements MemMemberService {
	@Autowired
	private DepositDao depositDao;

	@Autowired
	private MemMemberDao memMemberDao;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private MemReceiverDao memReceiverDao;

	@Autowired
	private CouponDao couponDao;

	@Autowired
	private CouponCodeDao couponCodeDao;

	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private MemberLoginStatusDao memberLoginStatusDao;

	@Value("${systemPath}")
	private String systemPath;

	@Value("${send.phone.loginName}")
	private String SEND_PHONE_LOGINNAME;

	@Value("${send.phone.pwd}")
	private String SEND_PHONE_PWD;

	@Value("${send.phone.url}")
	private String SEND_PHONE_URL;

	@Value("${send.phone.sign}")
	private String SEND_PHONE_SIGN;

	@Override
	public MemMember getMemMemberByUsername(String username)
			throws GenericException {
		return memMemberDao.getMemMemberByUsername(username);
	}

	@Override
	public MemMember getMemMemberByUsernameNew(String username) throws GenericException {
		return memMemberDao.getMemMemberByUsernameNew(username);
	}

	@Override
	public MemMember getCurrentUser(String userName) {
		MemMember user = this.getMemMemberByUsername(userName);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public int verifyPassword(String userName, String password) {
		return memMemberDao.verifyPassword(userName, password);
	}

	@Override
	public int handlerUserLoginPwdExcepton(String userName) {
		if (StringUtils.isNotEmpty(userName)) {
			MemMember user = memMemberDao.getMemMemberByUsername(userName);
			if (StringUtils.isNotEmpty(user.getLoginFailureCount())) {
				Integer errorNum = Integer
						.parseInt(user.getLoginFailureCount());
				user.setLoginFailureCount(String.valueOf((errorNum + 1)));
			} else {
				user.setLoginFailureCount("1");
			}
			// 如果连续输入密码5次错误，就锁定账户
			if (Integer.valueOf(user.getLoginFailureCount()) == 5) {
				user.setIsLocked("1");
			}
			// userDao.updateUser(user);
			return 1;
			// return Integer.valueOf(user.getExt2());
		}
		return 5;
	}

	@Transactional(readOnly = false)
	public void saveMember(MemberDto memberDto) throws Exception {
		MemMember member = new MemMember();
		BeanUtils.copyProperties(memberDto, member);
		member.setUsername(memberDto.getUserName());
		member.setId(UUIDGenerator.getUUID());
		member.setSessionId(UUIDGenerator.getUUID());
		String pwd = DigestUtil.MD5(member.getPassword());
		member.setPassword(pwd);
		// 微信号
		member.setWeixinName(memberDto.getWeixinName());
		memMemberDao.saveMember(member);
		
		//判断是否为第三方授权登录
		String authFlag = memberDto.getAuthFlag();
		if(StringUtils.isNotEmpty(authFlag)) {
			memMemberDao.saveMemberPreAuthInfo(member);
		}
		memberDto.setId(member.getId());
		UserSession.setSession("wxId", member.getId());

		/*** 注册赠送优惠券 ***/
		List<Coupon> list = couponDao
				.findUsableCouponByPresentType(PresentType.register_present
						.getIndex());
		produceCouponCodes(member.getId(), list);
	}
	
	/*app注册，密码不需要加密
	 * 
	 */
	
	@Transactional(readOnly = false)
	public void saveAppMember(MemberDto memberDto) throws Exception {
		MemMember member = new MemMember();
		BeanUtils.copyProperties(memberDto, member);
		member.setUsername(memberDto.getUserName());
		member.setId(UUIDGenerator.getUUID());
		member.setSessionId(UUIDGenerator.getUUID());
		//String pwd = DigestUtil.MD5(member.getPassword());
		member.setPassword(member.getPassword().toLowerCase());
		// 微信号
		member.setWeixinName(memberDto.getWeixinName());
		memMemberDao.saveMember(member);
		
		//判断是否为第三方授权登录
		String authFlag = memberDto.getAuthFlag();
		if(StringUtils.isNotEmpty(authFlag)) {
			memMemberDao.saveMemberPreAuthInfo(member);
		}

		UserSession.setSession("wxId", member.getId());

		/*** 注册赠送优惠券 ***/
		List<Coupon> list = couponDao
				.findUsableCouponByPresentType(PresentType.register_present
						.getIndex());
		produceCouponCodes(member.getId(), list);
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		RequestAttributes requestAttributes = RequestContextHolder
				.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
					.getRequest();
			Principal principal = (Principal) request.getSession()
					.getAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return true;
			}
		}
		return false;
	}

	public MemMember getCurrent() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
					.getRequest();
			Principal principal = (Principal) request.getSession()
					.getAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return memMemberDao.getMemMember(principal.getId());
			}
		}
		return null;
	}

	public String getCurrentUsername() {
		RequestAttributes requestAttributes = RequestContextHolder
				.currentRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = ((ServletRequestAttributes) requestAttributes)
					.getRequest();
			Principal principal = (Principal) request.getSession()
					.getAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME);
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	public boolean sendValidateCode(String phone, String email,
			String sendType, String validate) throws Exception {
		boolean flag = false;
		try {
			String content = SEND_PHONE_SIGN + "您好,验证码为:" + validate;
			if (!StringUtils.isEmpty(phone) && "1".equals(sendType)) {
				PhoneMessageUtil.sendPhoneMessage(SEND_PHONE_LOGINNAME,
						SEND_PHONE_PWD, SEND_PHONE_URL, content, phone);
			}
			if (!StringUtils.isEmpty(email) && "0".equals(sendType)) {
				MailBean mailBean = new MailBean();
				mailBean.setToEmails(new String[] { email });
				mailBean.setSubject("联保科技邮箱验证");
				Map<String, String> map = new HashMap<String, String>();
				map.put("validateCode", validate);
				map.put("systemPath", systemPath);
				mailBean.setData(map);
				mailSenderUtil.send(mailBean);
			}
			flag = true;
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean sendValidateCode(String phone, String email, String validate)
			throws Exception {
		boolean flag = false;
		try {
			String content = SEND_PHONE_SIGN + "您好,验证码为:" + validate;
			if (!StringUtils.isEmpty(phone)) {
				PhoneMessageUtil.sendPhoneMessage(SEND_PHONE_LOGINNAME,
						SEND_PHONE_PWD, SEND_PHONE_URL, content, phone);
			}
			if (!StringUtils.isEmpty(email)) {
				MailBean mailBean = new MailBean();
				mailBean.setToEmails(new String[] { email });
				mailBean.setSubject("联保科技邮箱验证");
				Map<String, String> map = new HashMap<String, String>();
				map.put("validateCode", validate);
				map.put("systemPath", systemPath);
				mailBean.setData(map);
				mailSenderUtil.send(mailBean);
			}
			flag = true;
		} catch (Exception ex) {
			flag = false;
			ex.printStackTrace();
		}
		return flag;
	}

	public List<OrderTable> getOrderTableByUserName(MemMember member)
			throws Exception {
		List<OrderTable> list = orderDao.getOrderTableByUserName(member);
		return list;
	}

	public List<Deposit> getAllDepositByUserName(MemMember member)
			throws Exception {
		return depositDao.getAllDepositByUserName(member);
	}

	public List<OrderTable> findOrderByKeyword(MemMember member)
			throws Exception {
		return orderDao.findOrderByKeyword(member);
	}

	public Integer getMemReceiverByAddrCount(String memberId)
			throws GenericException {
		return memReceiverDao.getMemReceiverByAddrCount(memberId);
	}

	public List<MemReceiver> getMemReceiverByAddress(String memberId)
			throws Exception {
		return memReceiverDao.getMemReceiverByAddress(memberId);
	}

	@Override
	public MemReceiver getMemReceiverById(String id) throws Exception {
		return memReceiverDao.getMemReceiverById(id);
	}

	@Override
	public List<CouponCode> getCouponByMemberId(String memberId)
			throws GenericException {
		return couponCodeDao.getCouponByMemberId(memberId);
	}

	@Override
	public Coupon getCouponByCouponId(String couponId) throws Exception {
		return couponDao.getCouponByCouponId(couponId);
	}

	@Override
	public List<Area> findAreasByParentId(String parentId) {
		return areaDao.findAreasByParentId(parentId);
	}

	@Override
	public void delete(String id) throws Exception {
		memReceiverDao.delete(id);

	}

	@Override
	public void update(MemReceiver memReceiver) throws Exception {
		memReceiverDao.update(memReceiver);
	}

	@Override
	public void save(MemReceiver memReceiver) throws Exception {
		memReceiverDao.save(memReceiver);
	}

	@Override
	public void updateIsDefault(MemReceiver memReceiver) throws Exception {
		memReceiverDao.updateIsDefault(memReceiver);
	}

	@Override
	public void saveMemberLoginStatus(MemberLoginStatus memberLoginStatus)
			throws Exception {
		memberLoginStatusDao.saveMemberLoginStatus(memberLoginStatus);
	}

	@Override
	public MemberLoginStatus findByMemberIdAndSessionId(String memberId,
			String sessionId) throws Exception {
		return memberLoginStatusDao.findByMemberIdAndSessionId(memberId,
				sessionId);
	}

	@Override
	public MemberLoginStatus findByMemberId(String memberId) throws Exception {
		return memberLoginStatusDao.findByMemberId(memberId);
	}

	@Override
	public MemMember getMemMember(String id) throws Exception {
		// TODO Auto-generated method stub
		return memMemberDao.getMemMember(id);
	}

	@Override
	public MemReceiver getDefaultMemReceiverByMemberId(String memberId)
			throws Exception {
		// TODO Auto-generated method stub
		return memReceiverDao.getDefaultMemReceiverByMemberId(memberId);
	}

	@Override
	public void updateById(Map<String, String> param) {
		memMemberDao.updateById(param);
	}
	
	@Override
	public Map<String, Object> getMemberbyCardId(String idCard) {
		return memMemberDao.getMemberbyCardId(idCard);
	}

	public Map<String, Object> findApproveInfo(String id) {
		return memMemberDao.findApproveInfo(id);
	}

	// 根据优惠券批量生成优惠码
	public void produceCouponCodes(String memberId, List<Coupon> list) {
		if (list.size() > 0) {
			List<CouponCode> couponCodeList = new ArrayList<CouponCode>();
			for (int i = 0; i < list.size(); i++) {
				CouponCode cc = new CouponCode();
				cc.setId(UUIDGenerator.getUUID());
				cc.setCode(UUIDGenerator.getUUID());
				cc.setCouponId(list.get(i).getId());
				cc.setMemberId(memberId);
				couponCodeList.add(cc);
			}
			couponCodeDao.insertList(couponCodeList); // 批量写入优惠券
			couponDao.updateMinusSum(list); // 批量更新优惠券数量-1
		}
	}

	@Override
	public MemMember memberLogin(String username, String phone)
			throws Exception {
		return memMemberDao.memberLogin(username, phone);
	}

	@Override
	public MemMember getMemberByPhone(String phone) throws Exception {
		return memMemberDao.getMemberByPhone(phone);
	}

	@Override
	public void updatePassword(String password, String username)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		memMemberDao.updatePassword(paramMap);
	}

	@Override
	public void updateIsUsedByCode(String code, Integer isUsed)
			throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("isUsed", isUsed);
		paramMap.put("code", code);
		couponCodeDao.updateIsUsedByCode(paramMap);
	}

	@Override
	public MemMember getApproveByUserName(String userName) throws Exception {
		return memMemberDao.getApproveByUserName(userName);
	}

	@Override
	public CouponCode getCouponCodeByCouponCode(String code) {
		return couponCodeDao.getCouponCodeByCouponCode(code);
	}
	
	/**
	 * 查询当前用户是否预授权
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public MemMember queryIsAuthByUserName(String userId,String authFlag) throws Exception {
		return memMemberDao.queryIsAuthByUserName(userId,authFlag);
	}
	
	/**
	 * 根据sessionId查询会员信息
	 * 
	 * @param memberId
	 * @return
	 * @throws Exception
	 */
	public MemMember getMemMemberBySessionId(String sessionId) throws Exception {
		return memMemberDao.getMemMemberBySessionId(sessionId);
	}

	@Override
	public void updateSessionId(String userName,String sessionId) throws Exception {
		memMemberDao.updateSessionId(userName,sessionId);
		
	}

	@Override
	public void updateCommission(String memMemberId, String commission)
			throws Exception {
		memMemberDao.updateCommission(memMemberId, commission);
		
	}

	@Override
	public void updatePayPassword(String id, String payPassword)
			throws Exception {
		memMemberDao.updatePayPassword(id, payPassword);
		
	}

	@Override
	public void updateMemberInfoById(Map<String, String> param)
			throws Exception {
		memMemberDao.updateMemberInfoById(param);
	}

	@Override
	public Object queryMemberByIdCard(String idCard) throws Exception {
		return memMemberDao.queryMemberByIdCard(idCard);
	}

	@Override
	public int queryMemberInfo(String userName, String password)throws Exception {
		return memMemberDao.queryMemberInfo(userName, password);
	}

	@Override
	public MemReceiver getLastOrderReceiverByMemberId(String userId)
			throws Exception {
		return memReceiverDao.getLastOrderReceiverByMemberId(userId);
	}
	
	@Override
	public List<Coupon> queryGetCoupon(String memberId, String page)throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		if (StringUtils.isNotBlank(page)) {
			int startSize = (Integer.valueOf(page) - 1) * 4;
			int pageSize = 4;
			map.put("startSize", startSize);
			map.put("pageSize", pageSize);
		}
    	map.put("memberId", memberId);
    	return couponDao.queryGetCoupon(map);
	}

	@Override
	public void insert(CouponCode couponCode) throws Exception {
		couponCodeDao.insert(couponCode);
	}
	
}
