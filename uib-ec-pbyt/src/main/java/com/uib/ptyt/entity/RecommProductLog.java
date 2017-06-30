package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;

/***
 * 推广记录下单明细表实体类
 * @author Administrator
 *
 */
public class RecommProductLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	/** 推广记录ID */
	private String recommendId;
	
	/** 推广人用户ID */
	private String recommMemberId;
	
	/** 购买人用户ID */
	private String memberId;
	
	private String productId;

	/** 订单编号 */
	private String orderNo;
	
	/** 是否结算：0-初始状态 ,1-未结算，2-已结算 */
	private int isSettlement;
	
	private Date createTime;
	
	private String delFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecommendId() {
		return recommendId;
	}

	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}

	public String getRecommMemberId() {
		return recommMemberId;
	}

	public void setRecommMemberId(String recommMemberId) {
		this.recommMemberId = recommMemberId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getIsSettlement() {
		return isSettlement;
	}

	public void setIsSettlement(int isSettlement) {
		this.isSettlement = isSettlement;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
