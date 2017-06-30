/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.order.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uib.product.entity.ProductSpecification;

/**
 * 订单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableItem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private OrderTable orderTable;		// 订单表ID 父类
	private String fullName;		// 商品全称
	private String isGift;		// 是否为赠品
	private String name;		// 商品名称
	private BigDecimal price;		// 商品价格
	private Integer quantity;		// 商品数量
	private Integer returnQuantity;		// 退货量
	private Integer shippedQuantity;		// 发货量
	private String goodsNo;		// 商品编号
	private String thumbnail;		// 缩略图
	private Integer weight;		// 商品重量
	private String orderNo;		// 订单编号
	private String cid;//评论表id
	
	private String remarks; // 备注
	private String createBy; // 创建者
	private Date createDate; // 创建日期
	private String updateBy; // 更新者
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	
	private String isComment;//是否已评价，0：未评价，1：已评价
	
	private String wxIsMarketable;//微信是否已下架
	private Integer stock;//可用库存
	private String orderType;  //订单类型：0-平台；1-推荐
	private String isSettlement;  //是否结算 1-未结算 2-已结算
	private String userType;
	private String supplierId;    //供应商编号
	private List<ProductSpecification> productSpecificationList; //商品规格
	
	public OrderTableItem() {
	}

	/*public OrderTableItem(String id){
		super(id);
	}*/

	
	public OrderTableItem(OrderTable orderTable){
		this.orderTable = orderTable;
	}

	public String getIsSettlement() {
		return isSettlement;
	}

	public void setIsSettlement(String isSettlement) {
		this.isSettlement = isSettlement;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	@Length(min=1, max=64, message="订单表ID长度必须介于 1 和 64 之间")
	public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}
	
	@Length(min=0, max=32, message="商品全称长度必须介于 0 和 32 之间")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Length(min=0, max=32, message="是否为赠品长度必须介于 0 和 32 之间")
	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}
	
	@Length(min=0, max=32, message="商品名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}
	
	public Integer getShippedQuantity() {
		return shippedQuantity;
	}

	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}
	
	@Length(min=0, max=32, message="商品编号长度必须介于 0 和 32 之间")
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	
	@Length(min=0, max=32, message="缩略图长度必须介于 0 和 32 之间")
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@JsonProperty
	@Transient
	public BigDecimal getSubtotal() {
		if (getPrice() != null && getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}

	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public String getWxIsMarketable() {
		return wxIsMarketable;
	}

	public void setWxIsMarketable(String wxIsMarketable) {
		this.wxIsMarketable = wxIsMarketable;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public List<ProductSpecification> getProductSpecificationList() {
		return productSpecificationList;
	}

	public void setProductSpecificationList(
			List<ProductSpecification> productSpecificationList) {
		this.productSpecificationList = productSpecificationList;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
}