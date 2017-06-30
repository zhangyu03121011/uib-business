package com.uib.mobile.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecommendProductLog {
	private String id;	//推荐记录id
	private String recommendId;
	private String memberId;	//被推荐会员id
	private String recommendMemberId;	//推荐会员id
	private String productId;	//商品id
	private String orderNo;//订单编号
	private Integer settlement;//是否结算(0：初始状态、1：未结算、2：结算)
	private Date createTime;	//推荐记录时间
	private Character delFlag;	//删除标志
	private Double commPercent; //佣金比例
	private Double price; // 销售价格
	private Double commission; // 佣金
	private String paymentDate; //支付完成日期
	private String cartItemId;
	
	/**不参与持久化,用于页面显示**/
	private String name;//被推荐人的微信昵称
	private String avatar;//被推荐人的微信头像
	private String createTimeFormat; //格式化时间显示
	private boolean flag;  //标志着查询佣金记录时 list为null是数据为空还是出错
	
	private Double sellPrice;  //销售价格
	private Double BSupplyPrice;  //B端供货价
	private int quantity;   //商品数量
	private String userType; //被推荐人用户类型
	private String recommendUserType; //推荐人用户类型
	
	
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
 
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getRecommendMemberId() {
		return recommendMemberId;
	}
	public void setRecommendMemberId(String recommendMemberId) {
		this.recommendMemberId = recommendMemberId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getCreateTime() {
		return createTime;
	}
    public String getCreateTimeFormat(){
    	if(null!=createTime){
    		return  new SimpleDateFormat("yyyy-MM-dd").format(createTime);
    	}else{
    		return "";
    	}	
    }
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Character getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Character delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getSettlement() {
		return settlement;
	}
	public void setSettlement(Integer settlement) {
		this.settlement = settlement;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getCommPercent() {
		return commPercent;
	}
	public void setCommPercent(Double commPercent) {
		this.commPercent = commPercent;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(String recommendId) {
		this.recommendId = recommendId;
	}
	public String getCartItemId() {
		return cartItemId;
	}
	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getBSupplyPrice() {
		return BSupplyPrice;
	}
	public void setBSupplyPrice(Double bSupplyPrice) {
		BSupplyPrice = bSupplyPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getRecommendUserType() {
		return recommendUserType;
	}
	public void setRecommendUserType(String recommendUserType) {
		this.recommendUserType = recommendUserType;
	}
	
}
