/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.uib.serviceUtils.Utils;
import com.uib.serviceUtils.Utils.RoundType;

/**
 * 配送方式Entity
 * @author limy
 * @version 2015-06-16
 */
public class ShippingMethod implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;		// 名称
	private Integer firstweight;		// 首重量
	private Integer continueweight;		// 续重量
	private Double firstprice;		// 首重价格
	private Double continueprice;		// 续重价格
	private String icon;		// 图标
	private String description;		// 介绍
	private DeliveryCorporation defaultdeliverycorp;		// 默认物流公司
	
	private String remarks; // 备注
	private String createBy; // 创建者
	private Date createDate; // 创建日期
	private String updateBy; // 更新者
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	
	public ShippingMethod() {
		super();
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

	public Integer getFirstweight() {
		return firstweight;
	}

	public void setFirstweight(Integer firstweight) {
		this.firstweight = firstweight;
	}

	public Integer getContinueweight() {
		return continueweight;
	}

	public void setContinueweight(Integer continueweight) {
		this.continueweight = continueweight;
	}

	public Double getFirstprice() {
		return firstprice;
	}

	public void setFirstprice(Double firstprice) {
		this.firstprice = firstprice;
	}

	public Double getContinueprice() {
		return continueprice;
	}

	public void setContinueprice(Double continueprice) {
		this.continueprice = continueprice;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DeliveryCorporation getDefaultdeliverycorp() {
		return defaultdeliverycorp;
	}

	public void setDefaultdeliverycorp(DeliveryCorporation defaultdeliverycorp) {
		this.defaultdeliverycorp = defaultdeliverycorp;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	/**
	 * 计算运费
	 * 
	 * @param weight
	 *            重量
	 * @return 运费
	 */
	@Transient
	public BigDecimal calculateFreight(Integer weight) {
		BigDecimal freight = new BigDecimal(0);
		if (weight != null) {
			if (weight <= getFirstweight() || new BigDecimal(getContinueprice()).compareTo(new BigDecimal(0)) == 0) {
				freight = new BigDecimal(getFirstprice());
			} else {
				double contiuneWeightCount = Math.ceil((weight - getFirstweight()) / (double) getContinueweight());
				freight = new BigDecimal(getFirstprice()).add(new BigDecimal(getContinueprice()).multiply(new BigDecimal(contiuneWeightCount)));
			}
		}
		return Utils.setScale(freight,RoundType.roundHalfUp,2);
	}

}