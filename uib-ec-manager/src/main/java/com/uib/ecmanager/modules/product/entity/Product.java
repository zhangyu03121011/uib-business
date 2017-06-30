/**
  * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.mem.entity.MemMerchant;
import com.uib.ecmanager.modules.mem.entity.MemRank;

/**
 * 商品Entity
 * 
 * @author gaven
 * @version 2015-06-01
 */
public class Product extends DataEntity<Product> {

	private static final long serialVersionUID = 1L;
	private String productNo; // 商品编号
	private Integer allocatedStock; // 已分配库存
	private Double cost; // 成本价
	private String fullName; // 全称
	private String originalName;	//商品原名
	private Integer hits; // 点击数
	private String image; // 展示图片
	private String introduction; // 介绍
	private String isGift; // 是否为赠品
	private String isList; // 是否列出
	private String pcIsMarketable; // pc是否上架
	private String wxIsMarketable; // 微信是否上架
	private String appIsMarketable; // app是否上架

	private String isTop; // 是否置顶
	private String isMemberPrice; // 是否会员价
	private String appHomeShow; // APP首页是否显示
	private String wxHomeShow; // 微信首页是否显示

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
	private List<ProductImageRef> productImageRefList = Lists.newArrayList(); // 图片列表
	private String productPropertyId; // 商品属性ID
	private String productParameterId; // 商品参数ID
	private List<String> productSpecificationIds; // 商品规格Ids
	private ProductCategory productCategory;
	private Brand brand;
	private MemMerchant memMerchant;
	private List<SpecificationGroup> specificationGoups;
	private List<ParameterGroup> parameterGroups;
	private String supplierId;	//供应商ID
	private String distributorPhone; //分享人手机号码
	private String distributorName;  //分享人
	/** 参数值 */
	private Map<ProductParameter, String> parameterValue = new HashMap<ProductParameter, String>();
	private Map<String, ProductPropertyRef> propertyRefMap = new HashMap<String, ProductPropertyRef>();
	private List<ProductSpecificationRef> productSpecificationRefs;
	private List<ProductPriceRef> productPriceRefList = Lists.newArrayList();  //商品会员价格关联表
	private List<ProductAreaRef> productAreaRefList = Lists.newArrayList();  //商品会员价格关联表
	private String specialId;
	private String updateFlag;
	private String[] areaId;
	private String[] type;
	public Product() {
		super();
	}

	public Product(String id) {
		super(id);
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Integer getAllocatedStock() {
		return allocatedStock;
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

	@Length(min = 0, max = 256, message = "全称长度必须介于 0 和 256 之间")
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

	@Length(min = 0, max = 256, message = "展示图片长度必须介于 0 和256 之间")
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

	@Length(min = 0, max = 1, message = "是否为赠品长度必须介于 0 和 1 之间")
	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}

	@Length(min = 0, max = 1, message = "是否列出长度必须介于 0 和 1 之间")
	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	@Length(min = 0, max = 1, message = "是否置顶长度必须介于 0 和 1 之间")
	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	@Length(min = 0, max = 1, message = "是否会员价长度必须介于 0 和 1 之间")
	public String getIsMemberPrice() {
		return isMemberPrice;
	}

	public void setIsMemberPrice(String isMemberPrice) {
		this.isMemberPrice = isMemberPrice;
	}

	@Length(min = 0, max = 1, message = "热销长度必须介于 0 和 1 之间")
	public String getHotSell() {
		return hotSell;
	}

	public void setHotSell(String hotSell) {
		this.hotSell = hotSell;
	}

	@Length(min = 0, max = 1, message = "最新长度必须介于 0 和 1 之间")
	public String getNewest() {
		return newest;
	}

	public void setNewest(String newest) {
		this.newest = newest;
	}

	@Length(min = 0, max = 1, message = "活动促销长度必须介于 0 和 1 之间")
	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	@Length(min = 0, max = 256, message = "搜索关键词长度必须介于 0 和 256 之间")
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

	@Length(min = 0, max = 256, message = "备注长度必须介于 0 和256 之间")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getMonthSalesDate() {
		return monthSalesDate;
	}

	public void setMonthSalesDate(Date monthSalesDate) {
		this.monthSalesDate = monthSalesDate;
	}

	@Length(min = 0, max = 128, message = "名称长度必须介于 0 和128 之间")
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

	@Length(min = 0, max = 32, message = "评分数长度必须介于 0 和 32 之间")
	public String getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(String scoreCount) {
		this.scoreCount = scoreCount;
	}

	@Length(min = 0, max = 128, message = "页面描述长度必须介于 0 和 128 之间")
	public String getSeoDescription() {
		return seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	@Length(min = 0, max = 128, message = "页面关键词长度必须介于 0 和128 之间")
	public String getSeoKeywords() {
		return seoKeywords;
	}

	public void setSeoKeywords(String seoKeywords) {
		this.seoKeywords = seoKeywords;
	}

	@Length(min = 0, max = 128, message = "页面标题长度必须介于 0 和 128 之间")
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

	@Length(min = 0, max = 32, message = "库存备注长度必须介于 0 和 32 之间")
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

	@Length(min = 0, max = 32, message = "单位长度必须介于 0 和 32 之间")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

	@Length(min = 0, max = 32, message = "分类编号长度必须介于 0 和 32 之间")
	public String getProductCategoryNo() {
		return productCategoryNo;
	}

	public void setProductCategoryNo(String productCategoryNo) {
		this.productCategoryNo = productCategoryNo;
	}

	@Length(min = 0, max = 32, message = "是否自营长度必须介于 0 和 32 之间")
	public String getProprietary() {
		return proprietary;
	}

	public void setProprietary(String proprietary) {
		this.proprietary = proprietary;
	}

	@Length(min = 0, max = 32, message = "商户号长度必须介于 0 和 32 之间")
	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	@Length(min = 1, max = 64, message = "分类主键ID长度必须介于 1 和 64 之间")
	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	@Length(min = 1, max = 64, message = "品牌主键ID长度必须介于 1 和 64 之间")
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public MemMerchant getMemMerchant() {
		return memMerchant;
	}

	public void setMemMerchant(MemMerchant memMerchant) {
		this.memMerchant = memMerchant;
	}

	public List<SpecificationGroup> getSpecificationGoups() {
		return specificationGoups;
	}

	public void setSpecificationGoups(List<SpecificationGroup> specificationGoups) {
		this.specificationGoups = specificationGoups;
	}

	public List<ParameterGroup> getParameterGroups() {
		return parameterGroups;
	}

	public void setParameterGroups(List<ParameterGroup> parameterGroups) {
		this.parameterGroups = parameterGroups;
	}

	public Map<ProductParameter, String> getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(Map<ProductParameter, String> parameterValue) {
		this.parameterValue = parameterValue;
	}

	public Map<String, ProductPropertyRef> getPropertyRefMap() {
		return propertyRefMap;
	}

	public void setPropertyRefMap(Map<String, ProductPropertyRef> propertyRefMap) {
		this.propertyRefMap = propertyRefMap;
	}

	public List<ProductSpecificationRef> getProductSpecificationRefs() {
		return productSpecificationRefs;
	}

	public void setProductSpecificationRefs(List<ProductSpecificationRef> productSpecificationRefs) {
		this.productSpecificationRefs = productSpecificationRefs;
	}

	public String getPcIsMarketable() {
		return pcIsMarketable;
	}

	public void setPcIsMarketable(String pcIsMarketable) {
		this.pcIsMarketable = pcIsMarketable;
	}

	public String getWxIsMarketable() {
		return wxIsMarketable;
	}

	public void setWxIsMarketable(String wxIsMarketable) {
		this.wxIsMarketable = wxIsMarketable;
	}

	public String getAppIsMarketable() {
		return appIsMarketable;
	}

	public void setAppIsMarketable(String appIsMarketable) {
		this.appIsMarketable = appIsMarketable;
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
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public List<ProductPriceRef> getProductPriceRefList() {
		return productPriceRefList;
	}

	public void setProductPriceRefList(List<ProductPriceRef> productPriceRefList) {
		this.productPriceRefList = productPriceRefList;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public List<ProductAreaRef> getProductAreaRefList() {
		return productAreaRefList;
	}

	public void setProductAreaRefList(List<ProductAreaRef> productAreaRefList) {
		this.productAreaRefList = productAreaRefList;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String[] getAreaId() {
		return areaId;
	}

	public void setAreaId(String[] areaId) {
		this.areaId = areaId;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public String getDistributorPhone() {
		return distributorPhone;
	}

	public void setDistributorPhone(String distributorPhone) {
		this.distributorPhone = distributorPhone;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}
	
}