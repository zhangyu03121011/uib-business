/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.order.entity.OrderTable;

/**
 * 会员表Entity
 * 
 * @author kevin
 * @version 2015-05-31
 */
public class MemMember extends DataEntity<MemMember> {

	private static final long serialVersionUID = 1L;
	private String name; // 姓名
	private String username; // 用户名
	private String password; // 密码
	private String phone; // 手机号
	private String address; // 地址
	private BigDecimal amount; // 消费金额
	private BigDecimal commission; //佣金
	private BigDecimal balance; // 余额
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
	private MemRank memRank; // 会员等级
	private String idCard; // 身份证号
	private String isEnabled; // 是否启用
	private String isLocked; // 是否锁定
	private List<OrderTable> orderTableList = Lists.newArrayList();
	private List<CouponCode> CouponCodeList = Lists.newArrayList();
	private String realName; // 真实姓名
	private String	idCardValid;  //身份证有效期
	private String idCardPositive; // 身份证正面url
	private String idCardOpposite; // 身份证反面url
	private String idCardHand; // 手持身份证图片
	private String approveFlag; // 审核标志
	private Date approveDate; //审核时间
	private String auditFailureDescription;// 审核失败描述
    private String userType; //用户类型  1.C1消费者 2.B分销商 3.C2消费者
    private BigDecimal sumamount;//总贡献值
    
	
	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public BigDecimal getSumamount() {
		return sumamount;
	}

	public void setSumamount(BigDecimal sumamount) {
		this.sumamount = sumamount;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public MemMember() {
		super();
	}

	public MemMember(String id) {
		super(id);
	}
 
	public String getIdCardValid() {
		return idCardValid;
	}

	public void setIdCardValid(String idCardValid) {
		this.idCardValid = idCardValid;
	}

	@Length(min = 1, max = 255, message = "姓名长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 32, message = "用户名长度必须介于 0 和 32 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Length(min = 1, max = 32, message = "密码长度必须介于 1 和 32 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 1, max = 11, message = "手机号长度必须介于 1 和 11 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 255, message = "地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Length(min = 1, max = 255, message = "邮箱地址长度必须介于 1 和 255 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 1, max = 1, message = "性别长度必须介于 1 和 1 之间")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	@Length(min = 0, max = 11, message = "连续登录失改次数长度必须介于 0 和 11 之间")
	public String getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(String loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	@Length(min = 0, max = 255, message = "最后登录ip长度必须介于 0 和 255 之间")
	public String getOginIp() {
		return oginIp;
	}

	public void setOginIp(String oginIp) {
		this.oginIp = oginIp;
	}

	@Length(min = 0, max = 20, message = "积分长度必须介于 0 和 20 之间")
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	@Length(min = 0, max = 255, message = "注册ip长度必须介于 0 和 255 之间")
	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSafeKeyExpire() {
		return safeKeyExpire;
	}

	public void setSafeKeyExpire(Date safeKeyExpire) {
		this.safeKeyExpire = safeKeyExpire;
	}

	@Length(min = 0, max = 255, message = "安全秘钥长度必须介于 0 和 255 之间")
	public String getSafeKeyValue() {
		return safeKeyValue;
	}

	public void setSafeKeyValue(String safeKeyValue) {
		this.safeKeyValue = safeKeyValue;
	}

	@Length(min = 1, max = 255, message = "邮编长度必须介于 1 和 255 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min = 0, max = 255, message = "地区长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Length(min = 0, max = 18, message = "身份证号长度必须介于 0 和 18 之间")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Length(min = 1, max = 1, message = "是否启用长度必须介于 1 和 1 之间")
	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Length(min = 1, max = 1, message = "是否锁定长度必须介于 1 和 1 之间")
	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public List<OrderTable> getOrderTableList() {
		return orderTableList;
	}

	public void setOrderTableList(List<OrderTable> orderTableList) {
		this.orderTableList = orderTableList;
	}

	public MemRank getMemRank() {
		return memRank;
	}

	public void setMemRank(MemRank memRank) {
		this.memRank = memRank;
	}

	public List<CouponCode> getCouponCodeList() {
		return CouponCodeList;
	}

	public void setCouponCodeList(List<CouponCode> couponCodeList) {
		CouponCodeList = couponCodeList;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public String getApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}

	public String getAuditFailureDescription() {
		return auditFailureDescription;
	}

	public void setAuditFailureDescription(String auditFailureDescription) {
		this.auditFailureDescription = auditFailureDescription;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}
	
	

}