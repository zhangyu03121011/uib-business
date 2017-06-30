/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import java.beans.Transient;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 退货单Entity
 * 
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableReturnsItem extends DataEntity<OrderTableReturnsItem> {

	private static final long serialVersionUID = 1L;
	private OrderTableReturns orderTableReturns; // 退货单ID 父类
	private String productNo; // 商品编号
	private String name; // 商品名称
	private Integer quantity; // 数量
	private String returnNo; // 退货单编号
	private String image;// 图片路径
	private String imgWebUrl;// 图片页面http路径，用于页面显示
	private String imgDiskUrl;// 图片物理路径，用于读取图片文件

	/**
	 * @return imgWebUrl
	 */
	public String getImgWebUrl() {
		return imgWebUrl;
	}

	/**
	 * @param imgWebUrl
	 *            要设置的 imgWebUrl
	 */
	public void setImgWebUrl(String imgWebUrl) {
		this.imgWebUrl = imgWebUrl;
	}

	/**
	 * @return imgDiskUrl
	 */
	public String getImgDiskUrl() {
		return imgDiskUrl;
	}

	/**
	 * @param imgDiskUrl
	 *            要设置的 imgDiskUrl
	 */
	public void setImgDiskUrl(String imgDiskUrl) {
		this.imgDiskUrl = imgDiskUrl;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public OrderTableReturnsItem() {
		super();
	}

	public OrderTableReturnsItem(String id) {
		super(id);
	}

	public OrderTableReturnsItem(OrderTableReturns orderTableReturns) {
		this.orderTableReturns = orderTableReturns;
	}

	@Length(min = 1, max = 64, message = "退货单ID长度必须介于 1 和 64 之间")
	public OrderTableReturns getOrderTableReturns() {
		return orderTableReturns;
	}

	public void setOrderTableReturns(OrderTableReturns orderTableReturns) {
		this.orderTableReturns = orderTableReturns;
	}

	@Length(min = 0, max = 32, message = "商品名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	@Length(min = 0, max = 11, message = "数量长度必须介于 0 和 11 之间")
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

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

}