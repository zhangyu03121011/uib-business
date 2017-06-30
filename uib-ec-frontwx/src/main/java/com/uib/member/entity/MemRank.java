/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.member.entity;



/**
 * 会员等级(单表)Entity
 * @author kevin
 * @version 2015-05-29
 */
public class MemRank  {
	
	private String name;		// 名称
	private String amount;		// 消费金额
	private String isDefult;		// 是否默认
	private String isSpecial;		// 是否特殊
	private String scale;		// 优惠比例
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getIsDefult() {
		return isDefult;
	}

	public void setIsDefult(String isDefult) {
		this.isDefult = isDefult;
	}
	
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}
	
	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
	
}