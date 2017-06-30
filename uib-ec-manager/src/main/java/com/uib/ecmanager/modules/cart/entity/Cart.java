/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.cart.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

import javax.validation.constraints.NotNull;

/**
 * 购物车Entity
 * @author gaven
 * @version 2015-06-30
 */
public class Cart extends DataEntity<Cart> {
	
	private static final long serialVersionUID = 1L;
	private String cartKey;		// 密钥
	private String memberId;		// 会员id
	private Date modifyDate;		// 修改时间
	
	public Cart() {
		super();
	}

	public Cart(String id){
		super(id);
	}

	@Length(min=1, max=255, message="密钥长度必须介于 1 和 255 之间")
	public String getCartKey() {
		return cartKey;
	}

	public void setCartKey(String cartKey) {
		this.cartKey = cartKey;
	}
	
	@Length(min=0, max=64, message="会员id长度必须介于 0 和 64 之间")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="修改时间不能为空")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}