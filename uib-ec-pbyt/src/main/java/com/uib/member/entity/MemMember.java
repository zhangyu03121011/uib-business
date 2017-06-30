/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.interceptor.MemberInterceptor;

/**
 * 会员表Entity
 * 
 * @author kevin
 * @version 2015-05-31
 */
public class MemMember implements UserDetails {
	/** "身份信息"参数名称 */
	public static final String PRINCIPAL_ATTRIBUTE_NAME = MemberInterceptor.class.getName() + ".PRINCIPAL";

	/** "用户名"Cookie名称 */
	public static final String USERNAME_COOKIE_NAME = "username";

	private static final long serialVersionUID = 1L;
	private String id;
	private String name; // 姓名
	private String realName; //真实姓名
	private String username; // 用户名
	private String avatar; // 头像
	private String password; // 密码
	private String phone; // 手机号
	private String address; // 地址
	private String amount; // 消费金额
	private String balance; // 余额
	private double commission;//佣金
	private Date birth; // 出生日期
	private String email; // 邮箱地址
	private String gender; // 性别
	private Date lockedDate; // 锁定日期
	private String loginFailureCount; // 连续登录失改次数
	private String oginIp; // 最后登录ip
	private String point; // 积分
	private String registerIp; // 注册ip
	private Date safeKeyExpire; // 安全密钥到期时间
	private String safeKeyValue; // 安全秘钥
	private String zipCode; // 邮编
	private String area; // 地区
	private String rankId; // 会员等级
	private String idCard; // 身份证号
	private String isLocked; // 是否锁定
	private String orderStatusType; // 订单状态类型
	private String FindOrder;
	private String sessionId;
	private String payPassword;//支付密码
	private String userType;   //用户类型
	private String openId;   //微信openId
	
	
	private String authFlag; //是否第三方预授权登录用户
	
	private String payPhone;//支付手机号码

	/**
	 * 微信登录名
	 */
	private String weixinName;
	
	/**
	 * 身份证有效期
	 */
	private String idCardValid;

	public String getIdCardValid() {
		return idCardValid;
	}

	public void setIdCardValid(String idCardValid) {
		this.idCardValid = idCardValid;
	}

	/**
	 * 身份证正面图片路径
	 */
	private String idCardPositive;
	private String createDate;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * 身份证反面图片路径
	 */
	private String idCardOpposite;

	/**
	 * 手持身份证图片路径
	 */
	private String idCardHand;

	private String approveFlag; // 审核标志

	/**
	 * 审核失败描述
	 */
	private String auditFailureDescription;

	/**
	 * 审核时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date approveDate;

	public String getAuditFailureDescription() {
		return auditFailureDescription;
	}

	public void setAuditFailureDescription(String auditFailureDescription) {
		this.auditFailureDescription = auditFailureDescription;
	}

	public String getIdCardPositive() {
		return idCardPositive;
	}

	public void setIdCardPositive(String idCardPositive) {
		this.idCardPositive = idCardPositive;
	}

	public String getIdCardOpposite() {
		return idCardOpposite;
	}

	public void setIdCardOpposite(String idCardOpposite) {
		this.idCardOpposite = idCardOpposite;
	}

	public String getIdCardHand() {
		return idCardHand;
	}

	public void setIdCardHand(String idCardHand) {
		this.idCardHand = idCardHand;
	}

	public String getFindOrder() {
		return FindOrder;
	}

	public void setFindOrder(String findOrder) {
		FindOrder = findOrder;
	}

	public String getOrderStatusType() {
		return orderStatusType;
	}

	public void setOrderStatusType(String orderStatusType) {
		this.orderStatusType = orderStatusType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public String getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(String loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public String getOginIp() {
		return oginIp;
	}

	public void setOginIp(String oginIp) {
		this.oginIp = oginIp;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public Date getSafeKeyExpire() {
		return safeKeyExpire;
	}

	public void setSafeKeyExpire(Date safeKeyExpire) {
		this.safeKeyExpire = safeKeyExpire;
	}

	public String getSafeKeyValue() {
		return safeKeyValue;
	}

	public void setSafeKeyValue(String safeKeyValue) {
		this.safeKeyValue = safeKeyValue;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getRankId() {
		return rankId;
	}

	public void setRankId(String rankId) {
		this.rankId = rankId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 如果锁定标示为1，就锁定账户
		if ("0".equals(this.isLocked) || null == this.isLocked) {
			return true;
		}
		return false;
	}

	/**
	 * 证书是否过期
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * 是否有效
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getWeixinName() {
		return weixinName;
	}

	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public String getPayPhone() {
		return payPhone;
	}

	public void setPayPhone(String payPhone) {
		this.payPhone = payPhone;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
    

	

}