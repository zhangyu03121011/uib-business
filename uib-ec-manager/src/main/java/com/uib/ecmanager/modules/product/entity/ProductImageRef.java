/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 商品Entity
 * @author gaven
 * @version 2015-06-01
 */
public class ProductImageRef extends DataEntity<ProductImageRef> {
	
	private static final long serialVersionUID = 1L;
	private String productNo;		// 商品编号
	private String filePath;		// 图片文件路径
	private String title;		// 标题
	private String source;		// 原图片
	private String large;		// 大图片
	private String medium;		// 中图片
	private String thumbnail;		// 缩略图
	private String orders;		// 排序
	private Product product;		// 商品主键ID 父类
	
	public ProductImageRef() {
		super();
	}

	public ProductImageRef(String id){
		super(id);
	}

	public ProductImageRef(Product product){
		this.product = product;
	}

	@Length(min=0, max=32, message="商品编号长度必须介于 0 和 32 之间")
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@Length(min=0, max=32, message="图片文件路径长度必须介于 0 和 32 之间")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	@Length(min=0, max=32, message="标题长度必须介于 0 和 32 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=32, message="原图片长度必须介于 0 和 32 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=32, message="大图片长度必须介于 0 和 32 之间")
	public String getLarge() {
		return large;
	}

	public void setLarge(String large) {
		this.large = large;
	}
	
	@Length(min=0, max=32, message="中图片长度必须介于 0 和 32 之间")
	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}
	
	@Length(min=0, max=32, message="缩略图长度必须介于 0 和 32 之间")
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}
	
	@Length(min=1, max=64, message="商品主键ID长度必须介于 1 和 64 之间")
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}