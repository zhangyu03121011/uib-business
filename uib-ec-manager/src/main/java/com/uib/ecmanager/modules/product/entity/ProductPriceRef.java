/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品会员价格关联表Entity
 * @author luogc
 * @version 2016-07-16
 */
public class ProductPriceRef extends DataEntity<ProductPriceRef> {
	
	private static final long serialVersionUID = 1L;
	private String productId;		// 商品id
	private BigDecimal sellPrice;		// 售价
	private BigDecimal bSupplyPrice;		// B端供货价
	private String rankId;		// 会员等级
	
	public ProductPriceRef() {
		super();
	}

	public ProductPriceRef(String id){
		super(id);
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public BigDecimal getbSupplyPrice() {
		return bSupplyPrice;
	}

	public void setbSupplyPrice(BigDecimal bSupplyPrice) {
		this.bSupplyPrice = bSupplyPrice;
	}

	@Length(min=1, max=64, message="会员等级长度必须介于 1 和 64 之间")
	public String getRankId() {
		return rankId;
	}

	public void setRankId(String rankId) {
		this.rankId = rankId;
	}
	
}