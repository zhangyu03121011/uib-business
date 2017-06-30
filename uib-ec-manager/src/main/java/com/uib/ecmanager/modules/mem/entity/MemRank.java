/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.product.entity.ProductPriceRef;

/**
 * 会员等级(单表)Entity
 * @author kevin
 * @version 2015-05-29
 */
public class MemRank extends DataEntity<MemRank> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String amount;		// 消费金额
	//private String isDefult;		// 是否默认
	private String isSpecial;		// 是否特殊
	//private String scale;		// 优惠比例
	private List<MemMember> memMemberList = Lists.newArrayList();	
	private ProductPriceRef productPriceRef; //商品会员价格关联表
	private int count;
	
	public MemRank() {
		super();
	}

	public MemRank(String id){
		super(id);
	}

	@Length(min=0, max=32, message="名称长度必须介于 0 和 32 之间")
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
	
/*	@Length(min=0, max=1, message="是否默认长度必须介于 0 和 1 之间")
	public String getIsDefult() {
		return isDefult;
	}

	public void setIsDefult(String isDefult) {
		this.isDefult = isDefult;
	}*/
	
	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}
	
	/*	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}*/

	public List<MemMember> getMemMemberList() {
		return memMemberList;
	}

	public void setMemMemberList(List<MemMember> memMemberList) {
		this.memMemberList = memMemberList;
	}

	public ProductPriceRef getProductPriceRef() {
		return productPriceRef;
	}

	public void setProductPriceRef(ProductPriceRef productPriceRef) {
		this.productPriceRef = productPriceRef;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}