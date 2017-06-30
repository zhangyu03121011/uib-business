package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;


public class OrderTableReturnsItemDto implements Serializable{
	private static final long serialVersionUID = -8501497691035685164L;
	
	private String id;                      //主键
	private String orderTableReturnsId;     //退货单ID
	private String productNo;               //商品编号
	private String name;                    //商品名称
	private Integer quantity;                //数量
	private String returnNo;               //退货单编号
	private String createBy;               //创建者
	private Date createDate;               //创建时间
	private String updateBy;               //更新者
	private Date updateDate;               //更新时间
	private String remarks;                 //备注信息
	private String delFlag;                //删除标记（0：正常；1：删除）
	private String image;                   //图片路径
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderTableReturnsId() {
		return orderTableReturnsId;
	}
	public void setOrderTableReturnsId(String orderTableReturnsId) {
		this.orderTableReturnsId = orderTableReturnsId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getReturnNo() {
		return returnNo;
	}
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}
