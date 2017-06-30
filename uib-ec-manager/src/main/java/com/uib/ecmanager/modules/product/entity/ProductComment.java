/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.mem.entity.MemMember;

/**
 * 商品评论Entity
 * @author gaven
 * @version 2015-10-22
 */
public class ProductComment extends DataEntity<ProductComment> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 评论内容
	private String ip;		// 评论的ip地址
	private String isGuests;		// 是否匿名 0.不是 1. 匿名评论
	private Integer score;		// 评分
	private String orderTableItemId;		// 订单项主键ID
	private String productId;		// 商品id
	private String memberId;		// 会员id
	private String reUserId;		// 回复人
	private String reContent;		// 回复内容
	private String reCommentId;		// 回复id
	private String contentType;		// 0 评价 1.咨询
	
	private Map<String,Object> params;
	
	public ProductComment() {
		super();
	}

	public ProductComment(String id){
		super(id);
	}

	@Length(min=0, max=5000, message="评论内容长度必须介于 0 和 5000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=32, message="评论的ip地址长度必须介于 0 和 32 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIsGuests() {
		return isGuests;
	}

	public void setIsGuests(String isGuests) {
		this.isGuests = isGuests;
	}
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	@Length(min=0, max=64, message="订单项主键ID长度必须介于 0 和 64 之间")
	public String getOrderTableItemId() {
		return orderTableItemId;
	}

	public void setOrderTableItemId(String orderTableItemId) {
		this.orderTableItemId = orderTableItemId;
	}
	
	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=64, message="会员id长度必须介于 0 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@Length(min=0, max=64, message="回复人长度必须介于 0 和 64 之间")
	public String getReUserId() {
		return reUserId;
	}

	public void setReUserId(String reUserId) {
		this.reUserId = reUserId;
	}
	
	@Length(min=0, max=5000, message="回复内容长度必须介于 0 和 5000 之间")
	public String getReContent() {
		return reContent;
	}

	public void setReContent(String reContent) {
		this.reContent = reContent;
	}
	
	@Length(min=0, max=64, message="回复id长度必须介于 0 和 64 之间")
	public String getReCommentId() {
		return reCommentId;
	}

	public void setReCommentId(String reCommentId) {
		this.reCommentId = reCommentId;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}