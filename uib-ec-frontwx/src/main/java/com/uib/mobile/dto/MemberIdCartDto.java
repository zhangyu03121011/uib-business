package com.uib.mobile.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 身份认证信息
 * 
 * @author kevin
 *
 */
public class MemberIdCartDto {

	private String id;

	private String name;

	private String userName;

	private String idCard;
	
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

	/**
	 * 身份证反面图片路径
	 */
	private String idCardOpposite;

	/**
	 * 手持身份证图片路径
	 */
	private String idCardHand;

	/**
	 * 审核标志 认证标识 0. 待审核 1.审核成功 2.审核失败 , 为空时未认证
	 */
	private String approveFlag;

	/**
	 * 审核失败描述
	 */
	private String auditFailureDescription;

	/**
	 * 审核时间
	 */
	private Date approveDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIdCard() {
//		if (!StringUtils.isEmpty(idCard)) {
//			int idCardLength = idCard.length();
//			if (idCardLength == 15) {
//				idCard = idCard.substring(0, 5) + "*******" + idCard.substring(11, 15);
//			} else {
//				idCard = idCard.substring(0, 5) + "*******" + idCard.substring(14, 18);
//			}
//		}
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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

}
