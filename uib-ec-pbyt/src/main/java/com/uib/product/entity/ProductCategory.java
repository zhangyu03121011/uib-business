package com.uib.product.entity;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * 分类信息
 * @author kevin
 *
 */
public class ProductCategory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String categoryNo;		// 商品分类编号
	private Integer orders;		// 排序
	private Integer grade;		// 层级
	private String name;		// 名称
	private String seoDescription;		// 页面描述
	private String seoKeywords;		// 页面关健字
	private String seoTitle;		// 页面标题
	private String imagePath;       // 图片路径
	private String merchantNo;		// 商户号
	private String parentId;		// 上级分类编号
	private String parentIds;		// 所以父级节点ID
	
	private String createBy;        // 创建者
	
	private Timestamp createDate;	//创建时间
	
	private String  updateBy;       //修改者
	
	private Timestamp updateDate;   //修改时间
	
	
	private String  remarks;       // 备注
	
	
	private String delFlag;		  // 删除标记
	
	private Integer number_product; //对应品牌的数量
	

	
	public Integer getNumber_product() {
		return number_product;
	}
	public void setNumber_product(Integer number_product) {
		this.number_product = number_product;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSeoDescription() {
		return seoDescription;
	}
	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}
	public String getSeoKeywords() {
		return seoKeywords;
	}
	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}
	public String getSeoTitle() {
		return seoTitle;
	}
	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
}
