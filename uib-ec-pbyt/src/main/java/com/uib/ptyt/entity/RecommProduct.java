package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;

/***
 * 推广记录主表实体类
 * @author zfan
 *
 */

public class RecommProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	/** 推广人用户ID */
	private String recommMemberId;
	
	/** 商品ID */
	private String productId;
	
	/** 浏览量 */
	private String viewCount;
	
	private String createTime;
	
	private String delFlag;
	
	private String rankId;//会员等级
	private String goods;//货品
	private String fullName;//商品名称
	private String pice;//价格	
	private String num;//交易量
    private String image;//商品图片
    private String userType;//分享人用户类型
    
	public String getUsertype() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPice() {
		return pice;
	}

	public void setPice(String pice) {
		this.pice = pice;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getRankId() {
		return rankId;
	}

	public void setRankId(String rankId) {
		this.rankId = rankId;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecommMemberId() {
		return recommMemberId;
	}

	public void setRecommMemberId(String recommMemberId) {
		this.recommMemberId = recommMemberId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
