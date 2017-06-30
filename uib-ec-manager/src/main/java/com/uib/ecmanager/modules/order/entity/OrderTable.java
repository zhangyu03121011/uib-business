/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;



import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.uib.ecmanager.common.enums.OrderStatus;
import com.uib.ecmanager.common.enums.PaymentStatus;
import com.uib.ecmanager.common.enums.ShippingStatus;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.coupon.entity.Coupon;
import com.uib.ecmanager.modules.coupon.entity.CouponCode;
import com.uib.ecmanager.modules.mem.entity.Deposit;
import com.uib.ecmanager.modules.mem.entity.MemMember;
import com.uib.ecmanager.modules.method.entity.PaymentMethod;
import com.uib.ecmanager.modules.method.entity.ShippingMethod;
import com.uib.ecmanager.modules.product.entity.Product;
import com.uib.ecmanager.modules.sys.entity.User;

/**
 * 订单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTable extends DataEntity<OrderTable> {
	
	/** 订单名称分隔符 */
	private static final String NAME_SEPARATOR = " ";
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		// 订单编号
	private String address;		// 地址
	private BigDecimal orderAmount; //订单金额
	private BigDecimal amountPaid;		// 已付金额
	private String areaName;		// 地区名称
	private String consignee;		// 收货人
	private BigDecimal couponDiscount;		// 优惠劵折扣
	private Date expire;		// 到期时间
	private BigDecimal fee;		// 支付手续费
	private String invoiceTitle;		// 发票抬头
	private Integer isAllocatedStock;		// 是否分配库存
	private String isInvoice;		// 是否开据发票
	private Date lockExpire;		// 锁定到期时间
	private String memo;		// 附言
	private BigDecimal offsetAmount;		// 调整金额
	private String orderStatus;		// 订单状态
	private String paymentMethodName;		// 支付方式名称
	private String paymentStatus;		// 支付状态
	private String phone;		// 电话
	private String point;		// 赠送积分
	private String promotion;		// 促销
	private BigDecimal promotionDiscount;		// 促销折扣
	private String shippingMethodName;		// 配送方式名称
	private Integer shippingStatus;		// 配送状态
	private BigDecimal tax;		// 税金
	private String zipCode;		// 邮编
	private String area;		// 地区
	private CouponCode couponCode;		// 优惠码
	private PaymentMethod paymentMethod;		// 支付方式
	private ShippingMethod shippingMethod;		// 配送方式
	private String userName;		// 用户名
	private String distributorPhone;
	private String distributorName;
	private BigDecimal freight;		//运费
	private String operator;			//操作员
	private MemMember memMember;	//会员
	private String orderSource;		//订单来源
	private Date shippingDate;		//订单来源
	private String orderStatusName; 
	private String paymentStatusName;
	private String shippingStatusName;
	private String orderSourceName;
	private List<OrderTableItem> orderTableItemList = Lists.newArrayList();		// 子表列表
	private List<OrderTablePayment> orderTablePaymentList = Lists.newArrayList();		// 子表列表
	private List<OrderTableRefunds> orderTableRefundsList = Lists.newArrayList();		// 子表列表
	private List<OrderTableShipping> orderTableShippingList = Lists.newArrayList();		// 子表列表
	private List<Coupon> couponList = Lists.newArrayList();		// 子表列表
	private List<Deposit> depositList = Lists.newArrayList();		// 子表列表
	private String nameOfArea;	//地区对应的名字
	private Product product;
	private String exceptionRemarks; //异常备注
	private String isRemarks; //异常备注
    private String memberNo;//会员id
    private String supplierName; //供应商名称
    private String supplierId;  //供应商编号
    
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public OrderTable() {
		super();
	}

	public OrderTable(String id){
		super(id);
	}

	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=32, message="地址长度必须介于 0 和 32 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	

	@Length(min=0, max=32, message="地区名称长度必须介于 0 和 32 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Length(min=0, max=32, message="收货人长度必须介于 0 和 32 之间")
	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public BigDecimal getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(BigDecimal couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	
	
	@Length(min=0, max=32, message="发票抬头长度必须介于 0 和 32 之间")
	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public Integer getIsAllocatedStock() {
		return isAllocatedStock;
	}
	public void setIsAllocatedStock(Integer isAllocatedStock) {
		this.isAllocatedStock = isAllocatedStock;
	}
	@Length(min=0, max=32, message="是否开据发票长度必须介于 0 和 32 之间")
	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLockExpire() {
		return lockExpire;
	}

	public void setLockExpire(Date lockExpire) {
		this.lockExpire = lockExpire;
	}
	
	@Length(min=0, max=32, message="附言长度必须介于 0 和 32 之间")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	public BigDecimal getOffsetAmount() {
		return offsetAmount;
	}
	public void setOffsetAmount(BigDecimal offsetAmount) {
		this.offsetAmount = offsetAmount;
	}
	
	
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderStatusName(){
		return OrderStatus.getDescription(orderStatus);
	}
	
	
	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	@Length(min=0, max=32, message="支付方式名称长度必须介于 0 和 32 之间")
	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}
	

	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getPaymentStatusName(){
		return PaymentStatus.getDescription(paymentStatus);
	}
	@Length(min=0, max=32, message="电话长度必须介于 0 和 32 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=32, message="赠送积分长度必须介于 0 和 32 之间")
	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}
	
	@Length(min=0, max=32, message="促销长度必须介于 0 和 32 之间")
	public String getPromotion() {
		return promotion;
	}
	
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public BigDecimal getPromotionDiscount() {
		return promotionDiscount;
	}
	public void setPromotionDiscount(BigDecimal promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}
	
	
	
	@Length(min=0, max=32, message="配送方式名称长度必须介于 0 和 32 之间")
	public String getShippingMethodName() {
		return shippingMethodName;
	}

	public void setShippingMethodName(String shippingMethodName) {
		this.shippingMethodName = shippingMethodName;
	}
	

	public Integer getShippingStatus() {
		return shippingStatus;
	}

	public void setShippingStatus(Integer shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	public String getShippingStatusName(){
		return shippingStatus==null?null:ShippingStatus.getDescription(shippingStatus);
	}
	

	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	
	@Length(min=0, max=32, message="邮编长度必须介于 0 和 32 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	
	@Length(min=0, max=32, message="用户名长度必须介于 0 和 32 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<OrderTableItem> getOrderTableItemList() {
		return orderTableItemList;
	}

	public void setOrderTableItemList(List<OrderTableItem> orderTableItemList) {
		this.orderTableItemList = orderTableItemList;
	}
	public List<OrderTablePayment> getOrderTablePaymentList() {
		return orderTablePaymentList;
	}

	public void setOrderTablePaymentList(List<OrderTablePayment> orderTablePaymentList) {
		this.orderTablePaymentList = orderTablePaymentList;
	}
	public List<OrderTableRefunds> getOrderTableRefundsList() {
		return orderTableRefundsList;
	}

	public void setOrderTableRefundsList(List<OrderTableRefunds> orderTableRefundsList) {
		this.orderTableRefundsList = orderTableRefundsList;
	}
	/**
	 * 获取订单名称
	 * 
	 * @return 订单名称
	 */
	public String getName() {
		StringBuffer name = new StringBuffer();
		if (getOrderTableItemList() != null) {
			for (OrderTableItem orderTableItem : getOrderTableItemList()) {
				if (orderTableItem != null && orderTableItem.getFullName() != null) {
					name.append(NAME_SEPARATOR).append(orderTableItem.getFullName());
				}
			}
			if (name.length() > 0) {
				name.deleteCharAt(0);
			}
		}
		return name.toString();
	}
	
	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	public int getWeight() {
		int weight = 0;
		if (getOrderTableItemList() != null) {
			for (OrderTableItem orderItem : getOrderTableItemList()) {
				if (orderItem != null) {
					weight += orderItem.getTotalWeight();
				}
			}
		}
		return weight;
	}
	/**
	 * 获取订单项
	 * 
	 * @param goodsNo
	 *            商品编号
	 * @return 订单项
	 */
	public OrderTableItem getOrderItem(String goodsNo) {
		if (goodsNo != null && getOrderTableItemList() != null) {
			for (OrderTableItem orderItem : getOrderTableItemList()) {
				if (orderItem != null && goodsNo.equalsIgnoreCase(orderItem.getProduct().getId())) {
					return orderItem;
				}
			}
		}
		return null;
	}
	

	/**
	 * 获取已发货数量
	 * 
	 * @return 已发货数量
	 */
	public int getShippedQuantity() {
		int shippedQuantity = 0;
		if (getOrderTableItemList() != null) {
			for (OrderTableItem orderItem : getOrderTableItemList()) {
				if (orderItem != null && orderItem.getShippedQuantity() != null) {
					shippedQuantity += orderItem.getShippedQuantity();
				}
			}
		}
		return shippedQuantity;
	}

	/**
	 * 获取已退货数量
	 * 
	 * @return 已退货数量
	 */
	public int getReturnQuantity() {
		int returnQuantity = 0;
		if (getOrderTableItemList() != null) {
			for (OrderTableItem orderItem : getOrderTableItemList()) {
				if (orderItem != null && orderItem.getReturnQuantity() != null) {
					returnQuantity += orderItem.getReturnQuantity();
				}
			}
		}
		return returnQuantity;
	}
	
	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	public int getQuantity() {
		int quantity = 0;
		if (getOrderTableItemList() != null) {
			for (OrderTableItem orderItem : getOrderTableItemList()) {
				if (orderItem != null && orderItem.getQuantity() != null) {
					quantity += orderItem.getQuantity();
				}
			}
		}
		return quantity;
	}
	
	/**
	 * 获取商品价格
	 * 
	 * @return 商品价格
	 */
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		if (getOrderTableItemList() != null) {
			for (OrderTableItem orderItem : getOrderTableItemList()) {
				if (orderItem != null && orderItem.getSubtotal() != null) {
					price = price.add(orderItem.getSubtotal());
				}
			}
		}
		return price;
	}

	/**
	 * 获取订单金额
	 * 
	 * @return 订单金额
	 */
	public BigDecimal getAmount() {
		BigDecimal amount = getPrice();
		if (getFee() != null) {
			amount = amount.add(getFee());
		}
		if (getFreight() != null) {
			amount = amount.add(getFreight());
		}
		if (getPromotionDiscount() != null) {
			amount = amount.subtract(getPromotionDiscount());
		}
		if (getCouponDiscount() != null) {
			amount = amount.subtract(getCouponDiscount());
		}
		if (getOffsetAmount() != null) {
			amount = amount.add(getOffsetAmount());
		}
		if (getTax() != null) {
			amount = amount.add(getTax());
		}
		return amount.compareTo(new BigDecimal(0)) > 0 ? amount : new BigDecimal(0);
	}
	/**
	 * 获取应付金额
	 * 
	 * @return 应付金额
	 */
	public BigDecimal getAmountPayable() {
		BigDecimal amountPayable = getAmount().subtract(getAmountPaid()==null?new BigDecimal(0):getAmountPaid());
		return amountPayable.compareTo(new BigDecimal(0)) > 0 ? amountPayable : new BigDecimal(0);
	}

	/**
	 * 是否已过期
	 * 
	 * @return 是否已过期
	 */
	public boolean isExpired() {
		return getExpire() != null && new Date().after(getExpire());
	}
	

	/**
	 * 判断是否已锁定
	 * 
	 * @param operator
	 *            操作员
	 * @return 是否已锁定
	 */
	public boolean isLocked(User operator) {
		return getLockExpire() != null && new Date().before(getLockExpire()) && ((operator != null && !operator.equals(getOperator())) || (operator == null && getOperator() != null));
	}

	/**
	 * 计算税金
	 * 
	 * @return 税金
	 	
	@Transient
	public BigDecimal calculateTax() {
		BigDecimal tax = new BigDecimal(0);
		Setting setting = SettingUtil.get();
		if (setting.getIsTaxPriceEnabled()) {
			BigDecimal amount = getPrice();
			if (getPromotionDiscount() != null) {
				amount = amount.subtract(getPromotionDiscount());
			}
			if (getCouponDiscount() != null) {
				amount = amount.subtract(getCouponDiscount());
			}
			if (getOffsetAmount() != null) {
				amount = amount.add(getOffsetAmount());
			}
			tax = amount.multiply(new BigDecimal(setting.getTaxRate().toString()));
		}
		return setting.setScale(tax);
	}
*/
	
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}


	public MemMember getMemMember() {
		return memMember;
	}

	public void setMemMember(MemMember memMember) {
		this.memMember = memMember;
	}

	public List<Coupon> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}

	public CouponCode getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(CouponCode couponCode) {
		this.couponCode = couponCode;
	}

	public List<Deposit> getDepositList() {
		return depositList;
	}

	public void setDepositList(List<Deposit> depositList) {
		this.depositList = depositList;
	}

	public String getNameOfArea() {
		return nameOfArea;
	}

	public void setNameOfArea(String nameOfArea) {
		this.nameOfArea = nameOfArea;
	}

	public void setPaymentStatusName(String paymentStatusName) {
		this.paymentStatusName = paymentStatusName;
	}

	public void setShippingStatusName(String shippingStatusName) {
		this.shippingStatusName = shippingStatusName;
	}

	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}

	public String getOrderSourceName() {
		return orderSourceName;
	}

	public void setOrderSourceName(String orderSourceName) {
		this.orderSourceName = orderSourceName;
	}


	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public List<OrderTableShipping> getOrderTableShippingList() {
		return orderTableShippingList;
	}

	public void setOrderTableShippingList(List<OrderTableShipping> orderTableShippingList) {
		this.orderTableShippingList = orderTableShippingList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getExceptionRemarks() {
		return exceptionRemarks;
	}

	public void setExceptionRemarks(String exceptionRemarks) {
		this.exceptionRemarks = exceptionRemarks;
	}

	public String getIsRemarks() {
		return isRemarks;
	}

	public void setIsRemarks(String isRemarks) {
		this.isRemarks = isRemarks;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
}