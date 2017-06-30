/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.sys.utils.Utils;

/**
 * 商品规格Entity
 * 
 * @author gaven
 * @version 2015-06-13
 */
public class SpecificationGroup extends DataEntity<SpecificationGroup> {

	private static final long serialVersionUID = 1L;
	private String name; // 规格组名称
	private String groupNo; // 规格组编号
	private Integer contentType; // 规格类型
	private Integer orders; // 排序
	// private String productCategoryNo; // 分类编号
	private String merchantNo; // 商户号
	private ProductCategory productCategory; // 分类
	private List<ProductSpecification> productSpecificationList = Lists.newArrayList(); // 子表列表

	public SpecificationGroup() {
		super();
	}

	public SpecificationGroup(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "规格组名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 32, message = "规格组编号长度必须介于 0 和 32 之间")
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
		return orders == null ? 0 : orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@Length(min = 0, max = 32, message = "商户号长度必须介于 0 和 32 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public void setProductCategoryId(String productCategoryId) {
		if (Utils.isBlank(productCategory)) {
			this.productCategory = new ProductCategory();
		}
		this.productCategory.setId(productCategoryId);
	}

	public String getProductCategoryId() {
		return Utils.isBlank(this.productCategory) ? null : this.getProductCategory().getId();
	}

	public List<ProductSpecification> getProductSpecificationList() {
		return productSpecificationList;
	}

	public void setProductSpecificationList(List<ProductSpecification> productSpecificationList) {
		this.productSpecificationList = productSpecificationList;
	}

	/**
	 * 位置
	 */
	public enum SpecificationContentType {
		TEXT("文本", 0), IMAGE("图片", 1);
		private String description;
		private int index;

		private SpecificationContentType(String description, int index) {
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

	public String getContentTypeName() {
		String typeName = "";
		if (contentType != null) {
			typeName = SpecificationContentType.getDescription(contentType);
		}
		return typeName;
	}

	// 前端字段
	private boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}