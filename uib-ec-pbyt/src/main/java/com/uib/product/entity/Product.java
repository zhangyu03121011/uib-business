/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.uib.serviceUtils.Utils;

/**
 * 商品Entity
 * 
 * @author gaven
 * @version 2015-06-16
 */
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id; // 商品ID
	private String productNo; // 商品编号
	private Integer allocatedStock; // 已分配库存
	private Double cost; // 成本价
	private String fullName; // 全称
	private String originalName;//商品副标题
	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	private Integer hits; // 点击数
	private String image; // 展示图片
	private String introduction; // 介绍
	private String isGift; // 是否为赠品
	private String isList; // 是否列出
	// private String isMarketable; // 是否上架
	private String isTop; // 是否置顶
	private String isMemberPrice; // 是否会员价
	private String hotSell; // 热销
	private String newest; // 最新
	private String promotion; // 活动促销
	private String keyword; // 搜索关键词
	private Double marketPrice; // 市场价
	private String memo; // 备注
	private Integer monthHits; // 月点击数
	private Date monthHitsDate; // 月点击数更新日期
	private Integer monthSales; // 月销售量
	private Date monthSalesDate; // 月销售量更新日期
	private String name; // 名称
	private Integer point; // 赠送积分
	private Double price; // 销售价格
	private Integer sales; // 销量
	private Double score; // 评分
	private String scoreCount; // 评分数
	private String seoDescription; // 页面描述
	private String seoKeywords; // 页面关键词
	private String seoTitle; // 页面标题
	private Integer stock; // 库存
	private String stockMemo; // 库存备注
	private Integer totalScore; // 总评分
	private String unit; // 单位
	private Integer weekHits; // 周点击数
	private Date weekHitsDate; // 周点击数更新日期
	private Integer weekSales; // 周销售量
	private Date weekSalesDate; // 周销售量更新日期
	private Double weight; // 重量
	private String goods; // 货品
	private String productCategoryNo; // 分类编号
	private String proprietary; // 是否自营
	private String merchantNo; // 商户号
	private String productCategoryId; // 分类主键ID
	private String brandId; // 品牌主键ID
	private List<ProductImageRef> productImageRefList = Lists.newArrayList();// 图片列表
	private String productPropertyId; // 商品属性ID
	private String productParameterId; // 商品参数ID
	private List<String> productSpecificationIds; // 商品规格Ids
	private ProductCategory productCategory;
	private List<SpecificationGroup> specificationGroups;
	private List<ProductComment> productComments;
	// private Brand brand;
	// private MemMerchant memMerchant;

	private String remarks; // 备注
	private String createBy; // 创建者
	private Date createDate; // 创建日期
	private String updateBy; // 更新者
	private Date updateDate; // 更新日期
	private String delFlag; // 删除标记（0：正常；1：删除；2：审核）
	private Double commPercent; // 佣金比例

	private String pcIsMarketable; // pc标识是否上架
	private String appIsMarketable; // app标识是否上架
	private String wxIsMarketable; // 微信标识是否上架
	private String appHomeShow; // app标识是否首页显示
	private String wxHomeShow; // 微信标识是否首页显示

	/** 商品评论总数，不参与持久化，只供查询时使用 */
	private Integer commentCount;

	private Double sellPrice; // 销售价格
	private String isRecommend;// 是否被当前的店铺推荐(我的店铺功能)
	
	private String supplierId; //供应商编号

	/** 参数值 */
	private Map<ParameterGroup, Map<ProductParameter, String>> parametersValue = new HashMap<ParameterGroup, Map<ProductParameter, String>>();
	/**
	 * 商品属性map集合
	 */
	private Map<PropertyGroup, ProductProperty> propertyValuesMap = new HashMap<PropertyGroup, ProductProperty>();

	public Product() {
	}

	public Product(String id) {
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Integer getAllocatedStock() {
		return Utils.isBlank(allocatedStock) ? 0 : allocatedStock;
	}

	public void setAllocatedStock(Integer allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}

	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getIsMemberPrice() {
		return isMemberPrice;
	}

	public void setIsMemberPrice(String isMemberPrice) {
		this.isMemberPrice = isMemberPrice;
	}

	public String getHotSell() {
		return hotSell;
	}

	public void setHotSell(String hotSell) {
		this.hotSell = hotSell;
	}

	public String getNewest() {
		return newest;
	}

	public void setNewest(String newest) {
		this.newest = newest;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getMonthHits() {
		return monthHits;
	}

	public void setMonthHits(Integer monthHits) {
		this.monthHits = monthHits;
	}

	public Date getMonthHitsDate() {
		return monthHitsDate;
	}

	public void setMonthHitsDate(Date monthHitsDate) {
		this.monthHitsDate = monthHitsDate;
	}

	public Integer getMonthSales() {
		return monthSales;
	}

	public void setMonthSales(Integer monthSales) {
		this.monthSales = monthSales;
	}

	public Date getMonthSalesDate() {
		return monthSalesDate;
	}

	public void setMonthSalesDate(Date monthSalesDate) {
		this.monthSalesDate = monthSalesDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSales() {
		return sales;
	}

	public void setSales(Integer sales) {
		this.sales = sales;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(String scoreCount) {
		this.scoreCount = scoreCount;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getStockMemo() {
		return stockMemo;
	}

	public void setStockMemo(String stockMemo) {
		this.stockMemo = stockMemo;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getWeekHits() {
		return weekHits;
	}

	public void setWeekHits(Integer weekHits) {
		this.weekHits = weekHits;
	}

	public Date getWeekHitsDate() {
		return weekHitsDate;
	}

	public void setWeekHitsDate(Date weekHitsDate) {
		this.weekHitsDate = weekHitsDate;
	}

	public Integer getWeekSales() {
		return weekSales;
	}

	public void setWeekSales(Integer weekSales) {
		this.weekSales = weekSales;
	}

	public Date getWeekSalesDate() {
		return weekSalesDate;
	}

	public void setWeekSalesDate(Date weekSalesDate) {
		this.weekSalesDate = weekSalesDate;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getProductCategoryNo() {
		return productCategoryNo;
	}

	public void setProductCategoryNo(String productCategoryNo) {
		this.productCategoryNo = productCategoryNo;
	}

	public String getProprietary() {
		return proprietary;
	}

	public void setProprietary(String proprietary) {
		this.proprietary = proprietary;
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

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public List<ProductImageRef> getProductImageRefList() {
		return productImageRefList;
	}

	public void setProductImageRefList(List<ProductImageRef> productImageRefList) {
		this.productImageRefList = productImageRefList;
	}

	public String getProductPropertyId() {
		return productPropertyId;
	}

	public void setProductPropertyId(String productPropertyId) {
		this.productPropertyId = productPropertyId;
	}

	public String getProductParameterId() {
		return productParameterId;
	}

	public void setProductParameterId(String productParameterId) {
		this.productParameterId = productParameterId;
	}

	public List<String> getProductSpecificationIds() {
		return productSpecificationIds;
	}

	public void setProductSpecificationIds(List<String> productSpecificationIds) {
		this.productSpecificationIds = productSpecificationIds;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
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

	public List<SpecificationGroup> getSpecificationGroups() {
		return specificationGroups;
	}

	public void setSpecificationGroups(
			List<SpecificationGroup> specificationGroups) {
		this.specificationGroups = specificationGroups;
	}

	public Map<ParameterGroup, Map<ProductParameter, String>> getParametersValue() {
		return parametersValue;
	}

	public void setParametersValue(
			Map<ParameterGroup, Map<ProductParameter, String>> parametersValue) {
		this.parametersValue = parametersValue;
	}

	public Map<PropertyGroup, ProductProperty> getPropertyValuesMap() {
		return propertyValuesMap;
	}

	public void setPropertyValuesMap(
			Map<PropertyGroup, ProductProperty> propertyValuesMap) {
		this.propertyValuesMap = propertyValuesMap;
	}

	public List<ProductComment> getProductComments() {
		return productComments;
	}

	public void setProductComments(List<ProductComment> productComments) {
		this.productComments = productComments;
	}

	public Integer getProductCommentCount() {
		return Utils.isBlank(productComments) ? 0 : productComments.size();
	}

	/**
	 * 获取可用库存
	 * 
	 * @return 可用库存
	 */
	@Transient
	public Integer getAvailableStock() {
		Integer availableStock = null;
		if (getStock() != null && getAllocatedStock() != null) {
			availableStock = getStock() - getAllocatedStock();
			if (availableStock < 0) {
				availableStock = 0;
			}
		}
		return availableStock;
	}

	/**
	 * 获取缩略图
	 * 
	 * @return 缩略图
	 */
	@JsonProperty
	@Transient
	public String getThumbnail() {
		if (getProductImageRefList() != null
				&& !getProductImageRefList().isEmpty()) {
			return getProductImageRefList().get(0).getThumbnail();
		}
		return null;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Double getCommPercent() {
		return commPercent;
	}

	public void setCommPercent(Double commPercent) {
		this.commPercent = commPercent;
	}

	public String getPcIsMarketable() {
		return pcIsMarketable;
	}

	public void setPcIsMarketable(String pcIsMarketable) {
		this.pcIsMarketable = pcIsMarketable;
	}

	public String getAppIsMarketable() {
		return appIsMarketable;
	}

	public void setAppIsMarketable(String appIsMarketable) {
		this.appIsMarketable = appIsMarketable;
	}

	public String getWxIsMarketable() {
		return wxIsMarketable;
	}

	public void setWxIsMarketable(String wxIsMarketable) {
		this.wxIsMarketable = wxIsMarketable;
	}

	public String getAppHomeShow() {
		return appHomeShow;
	}

	public void setAppHomeShow(String appHomeShow) {
		this.appHomeShow = appHomeShow;
	}

	public String getWxHomeShow() {
		return wxHomeShow;
	}

	public void setWxHomeShow(String wxHomeShow) {
		this.wxHomeShow = wxHomeShow;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

}