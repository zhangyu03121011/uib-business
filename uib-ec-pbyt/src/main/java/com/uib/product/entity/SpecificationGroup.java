/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * 商品规格Entity
 * @author gaven
 * @version 2015-06-13
 */
public class SpecificationGroup implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;		// 规格组名称
	private String groupNo;		// 规格组编号
	private Integer contentType;		// 规格类型
	private Integer orders;		// 排序
	private String productCategoryNo;		// 分类编号
	private String merchantNo;		// 商户号
	private String productCategoryId;		// 分类主键ID
	private List<ProductSpecification> productSpecificationList = Lists.newArrayList();		// 子表列表
	
	private String remarks; // 备注
	private String createBy; // 创建者
	private Date createDate; // 创建日期
	private String updateBy; // 更新者
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	
	public SpecificationGroup() {
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

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getProductCategoryNo() {
		return productCategoryNo;
	}

	public void setProductCategoryNo(String productCategoryNo) {
		this.productCategoryNo = productCategoryNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public List<ProductSpecification> getProductSpecificationList() {
		return productSpecificationList;
	}

	public void setProductSpecificationList(
			List<ProductSpecification> productSpecificationList) {
		this.productSpecificationList = productSpecificationList;
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
	
	//前端字段
	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}



	/**
	 * 位置
	 */
	public enum SpecificationContentType{
		TEXT("文本",0),
		IMAGE("图片",1);
		private String description;
		private int index;
		private SpecificationContentType(String description,int index) {
			this.description = description;
			this.index = index;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		
		// 普通方法
        public static String getDescription(int index) {
            for (SpecificationContentType type : SpecificationContentType.values()) {
                if (type.getIndex() == index) {
                    return type.description;
                }
            }
            return null;
        }
	}
	
	public String getContentTypeName(){
		String typeName = "";
		if(contentType!=null){
			typeName = SpecificationContentType.getDescription(contentType);
		}
		return typeName;
	}
}