/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import java.beans.Transient;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uib.ecmanager.common.persistence.TreeEntity;

/**
 * 商品分类Entity
 * @author kevin
 * @version 2015-06-12
 */
public class ProductCategory extends TreeEntity<ProductCategory> {
	
	private static final long serialVersionUID = 1L;
	private String categoryNo;		// 商品分类编号
	private Integer orders;		// 排序
	private Integer grade;		// 层级
	private String name;		// 名称
	private String seoDescription;		// 页面描述
	private String seoKeywords;		// 页面关健字
	private String seoTitle;		// 页面标题
	private String merchantNo;		// 商户号
	private ProductCategory parent;		// 上级分类编号
	private String parentIds;		// 所以父级节点ID
	private String imagePath;	//图片路径
	
	/** 访问路径前缀 */
	private static final String PATH_PREFIX = "/goods/list";
	
	public ProductCategory() {
		super();
	}

	public ProductCategory(String id){
		super(id);
	}

	@Length(min=1, max=32, message="商品分类编号长度必须介于 1 和 32 之间")
	public String getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	
	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
	@Length(min=0, max=32, message="名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=32, message="页面描述长度必须介于 0 和 32 之间")
	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	
	@Length(min=0, max=32, message="页面关健字长度必须介于 0 和 32 之间")
	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	
	@Length(min=0, max=32, message="页面标题长度必须介于 0 和 32 之间")
	public String getSeoTitle() {
		return seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	
	@Length(min=0, max=32, message="商户号长度必须介于 0 和 32 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	@JsonBackReference
	public ProductCategory getParent() {
		return parent;
	}

	public void setParent(ProductCategory parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="所以父级节点ID长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * 获取访问路径
	 * 
	 * @return 访问路径
	 */
	@Transient
	public String getPath() {
		if (getId() != null) {
			return PATH_PREFIX + "?productCategoryId=" + getId();
		}
		return null;
	}
}