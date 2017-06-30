package com.uib.ecmanager.modules.product.entity;
///**
// * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
// */
//package com.uib.ecmanager.modules.product.entity;
//
//import org.hibernate.validator.constraints.Length;
//
//import com.uib.ecmanager.common.persistence.DataEntity;
//
///**
// * 商品Entity
// * @author gaven
// * @version 2015-06-01
// */
//public class ProductReview extends DataEntity<ProductReview> {
//	
//	private static final long serialVersionUID = 1L;
//	private String content;		// 评论内容
//	private String ip;		// 评论的ip地址
//	private String isShow;		// 是否显示 1:是 0:否
//	private Integer score;		// 评分
//	private String useName;		// 会员帐号
//	private String productNo;		// 商品编号
//	private Product product;		// 商品主键ID 父类
//	
//	public ProductReview() {
//		super();
//	}
//
//	public ProductReview(String id){
//		super(id);
//	}
//
//	public ProductReview(Product product){
//		this.product = product;
//	}
//
//	@Length(min=0, max=5000, message="评论内容长度必须介于 0 和 5000 之间")
//	public String getContent() {
//		return content;
//	}
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//	
//	@Length(min=0, max=32, message="评论的ip地址长度必须介于 0 和 32 之间")
//	public String getIp() {
//		return ip;
//	}
//
//	public void setIp(String ip) {
//		this.ip = ip;
//	}
//	
//	@Length(min=0, max=1, message="是否显示 1:是 0:否长度必须介于 0 和 1 之间")
//	public String getIsShow() {
//		return isShow;
//	}
//
//	public void setIsShow(String isShow) {
//		this.isShow = isShow;
//	}
//	
//	public Integer getScore() {
//		return score;
//	}
//
//	public void setScore(Integer score) {
//		this.score = score;
//	}
//	
//	@Length(min=0, max=32, message="会员帐号长度必须介于 0 和 32 之间")
//	public String getUseName() {
//		return useName;
//	}
//
//	public void setUseName(String useName) {
//		this.useName = useName;
//	}
//	
//	@Length(min=0, max=32, message="商品编号长度必须介于 0 和 32 之间")
//	public String getProductNo() {
//		return productNo;
//	}
//
//	public void setProductNo(String productNo) {
//		this.productNo = productNo;
//	}
//	
//	@Length(min=1, max=64, message="商品主键ID长度必须介于 1 和 64 之间")
//	public Product getProduct() {
//		return product;
//	}
//
//	public void setProduct(Product product) {
//		this.product = product;
//	}
//	
//}