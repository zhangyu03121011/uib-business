package com.uib.member.dto;

import java.util.Date;

public class MemberDto {
	private String id;
	private String name;		// 姓名
	private String userName;		// 用户名
	private String password;		// 密码
	private String phone;		// 手机号
	private String address;		// 地址
	private String amount;		// 消费金额
	private String balance;		// 余额
	private double commission;//佣金
	private Date birth;		// 出生日期
	private String email;		// 邮箱地址
	private String gender;		// 性别
	private Date lockedDate;		// 锁定日期
	private String loginFailureCount;		// 连续登录失改次数
	private String oginIp;		// 最后登录ip
	private String point;		// 积分
	private String registerIp;		// 注册ip
	private Date safeKeyExpire;		// 安全密钥到期时间
	private String safeKeyValue;		// 安全秘钥
	private String zipCode;		// 邮编
	private String area;		// 地区
	private String rankId;		// 会员等级
	private String idCard;		// 身份证号
	private String isLocked;		// 是否锁定
	private String sessionId;
	private String payPassword;//支付密码
	private String payPhone;//支付手机号码
	/**
	 * 微信登录名
	 */
	private String weixinName;
	
	private String authFlag;
	
//	
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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
	public String getPayPhone() {
		return payPhone;
	}
	public void setPayPhone(String payPhone) {
		this.payPhone = payPhone;
	}
	

	
}
