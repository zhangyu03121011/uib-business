package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.uib.product.entity.Product;



public class RecommOrderDto implements Serializable{	
	private static final long serialVersionUID = -8501497691035685164L;
	private  String memberId;//用户ID
    private  String avater;//购买用户头像
	private  String orderStatus;//订单状态
    private  String creatTime;//订单时间
    private  String memberName; //购买用户姓名
    private  String sellPice;//售价
    private  String bPrice;//b端进货价
    private  String productId;//商品ID
    private  String time;//时间
    private  String commission;//佣金
    private  String fullName;//商品名称
    private  String image;//商品图片
    private  String allPice;//总金额
    private  String allQuantity;//订单总数量  
    public String getAllQuantity() {
		return allQuantity;
	}
	public void setAllQuantity(String allQuantity) {
		this.allQuantity = allQuantity;
	}
	private  String quantity;//商品数量
    private  List<GoodsDto>   goods;//商品 
    private  String orderNo;//订单编号
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<GoodsDto> getGoods() {
		return goods;
	}
	public void setGoods(List<GoodsDto> goods) {
		this.goods = goods;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getAllPice() {
		return allPice;
	}
	public void setAllPice(String allPice) {
		this.allPice = allPice;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getAvater() {
		return avater;
	}
	public void setAvater(String avater) {
		this.avater = avater;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getSellPice() {
		return sellPice;
	}
	public void setSellPice(String sellPice) {
		this.sellPice = sellPice;
	}
	public String getbPrice() {
		return bPrice;
	}
	public void setbPrice(String bPrice) {
		this.bPrice = bPrice;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
    
}
