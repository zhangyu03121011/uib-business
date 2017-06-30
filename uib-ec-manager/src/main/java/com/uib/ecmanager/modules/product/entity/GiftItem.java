/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 赠品Entity
 * @author gaven
 * @version 2015-06-09
 */
public class GiftItem extends DataEntity<GiftItem> {
	
	private static final long serialVersionUID = 1L;
	private Integer quantity;		// 数量
	private String productId;		// 商品ID
	private String promotionId;		// 促销ID
	private Date modifyDate;		// 修改日期
	public final static String IS_GIFT="1";
	
	public GiftItem() {
		super();
	}

	public GiftItem(String id){
		super(id);
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=0, max=64, message="促销ID长度必须介于 0 和 64 之间")
	public String getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}