/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.common.utils.excel.annotation.ExcelField;
import com.uib.ecmanager.modules.mem.entity.WithdrawalApplyFor;

/**
 * 退货单Entity
 * 
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableReturns extends DataEntity<OrderTableReturns> {

	private static final long serialVersionUID = 1L;
	private String returnNo; // 退货单编号
	private String address; // 地址
	private String area; // 地区
	private String deliveryCorp; // 物流公司
	private BigDecimal freight; // 物流费用
	private String operator; // 操作员
	private String phone; // 电话
	private String shipper; // 发货人
	private String shippingMethod; // 配送方式
	private String trackingNo; // 运单号
	private String zipCode; // 邮编
	private String orderNo; // 订单编号
	private List<OrderReturnsRef> orderReturnsRefList = Lists.newArrayList(); // 子表列表
	private List<OrderTableReturnsItem> orderTableReturnsItemList = Lists
			.newArrayList(); // 子表列表
	private int returnType;// 退货类型：1退款 2退货 3换货
	private String storeId;// 分销商用户名
	private int returnStatus;// 退货状态（1已处理 2无法退货 3未处理）
	private Date applyTime;// 申请时间
	private String orderTableId;// 订单ID
	private Date applyBginTime;// 申请退货起始时间（用于查询）
	private Date applyEndTime;// 申请退货起始时间（用于查询）
	private String operaTion;// 操作（用于详情或者编辑）
	private String userName;// 用户名
	private String productId;// 商品编号
	private String returnReason;// 退货原因
	private String returnSum;// 退货金额
	private String retrunDesc;// 退货说明
	private String returnStatusStr;// 退货状态(用于导出到excel显示文字)
	private String returnTypeStr;// 退货类型(用于导出到excel显示文字)
	
    /**用于页面展示，不参与持久化**/
	private WithdrawalApplyFor withdrawalApplyFor; //用户申请退货的银行卡信息
	private String companyName;//供应商公司名称
	private String supplierId; //供用商编号
	/**
	 * @return returnStatusStr
	 */
	@ExcelField(title = "退货状态", align = 2, sort = 60)
	public String getReturnStatusStr() {
		return returnStatusStr;
	}

	/**
	 * @param returnStatusStr
	 *            要设置的 returnStatusStr
	 */
	public void setReturnStatusStr(String returnStatusStr) {
		this.returnStatusStr = returnStatusStr;
	}

	/**
	 * @return returnTypeStr
	 */
	@ExcelField(title = "退货类型", align = 2, sort = 40)
	public String getReturnTypeStr() {
		return returnTypeStr;
	}

	/**
	 * @param returnTypeStr
	 *            要设置的 returnTypeStr
	 */
	public void setReturnTypeStr(String returnTypeStr) {
		this.returnTypeStr = returnTypeStr;
	}

	@ExcelField(title = "用户名", align = 2, sort = 30)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@ExcelField(title = "商品编号", align = 2, sort = 15)
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@ExcelField(title = "退货理由", align = 2, sort = 45)
	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	@ExcelField(title = "退货金额", align = 2, sort = 50)
	public String getReturnSum() {
		return returnSum;
	}

	public void setReturnSum(String returnSum) {
		this.returnSum = returnSum;
	}

	@ExcelField(title = "退货说明", align = 2, sort = 55)
	public String getRetrunDesc() {
		return retrunDesc;
	}

	public void setRetrunDesc(String retrunDesc) {
		this.retrunDesc = retrunDesc;
	}

	public String getOperaTion() {
		return operaTion;
	}

	public void setOperaTion(String operaTion) {
		this.operaTion = operaTion;
	}

	public Date getApplyBginTime() {
		return applyBginTime;
	}

	public void setApplyBginTime(Date applyBginTime) {
		this.applyBginTime = applyBginTime;
	}

	public Date getApplyEndTime() {
		return applyEndTime;
	}

	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}

	public String getOrderTableId() {
		return orderTableId;
	}

	public void setOrderTableId(String orderTableId) {
		this.orderTableId = orderTableId;
	}

	@ExcelField(title = "退货时间", align = 2, sort = 25)
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

//	@ExcelField(title = "分销商用户名", align = 2, sort = 20)
//	public String getStoreId() {
//		return storeId;
//	}
//
//	public void setStoreId(String storeId) {
//		this.storeId = storeId;
//	}

	public int getReturnType() {
		return returnType;
	}

	public void setReturnType(int returnType) {
		this.returnType = returnType;
	}

	public OrderTableReturns() {
		super();
	}

	public OrderTableReturns(String id) {
		super(id);
	}

	@Length(min = 0, max = 32, message = "退货单编号长度必须介于 0 和 32 之间")
	@ExcelField(title = "退货单编号", align = 2, sort = 5)
	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

	@Length(min = 1, max = 32, message = "地址长度必须介于 1 和 32 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min = 1, max = 32, message = "地区长度必须介于 1 和 32 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Length(min = 1, max = 32, message = "物流公司长度必须介于 1 和 32 之间")
	public String getDeliveryCorp() {
		return deliveryCorp;
	}

	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}

	@Transient
	public BigDecimal getFreight() {
		return freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	@Length(min = 1, max = 32, message = "操作员长度必须介于 1 和 32 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Length(min = 1, max = 32, message = "电话长度必须介于 1 和 32 之间")
	@ExcelField(title = "用户电话", align = 2, sort = 35)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 1, max = 32, message = "发货人长度必须介于 1 和 32 之间")
	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	@Length(min = 0, max = 32, message = "配送方式长度必须介于 0 和 32 之间")
	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	@Length(min = 0, max = 32, message = "运单号长度必须介于 0 和 32 之间")
	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	@Length(min = 1, max = 32, message = "邮编长度必须介于 1 和 32 之间")
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Length(min = 1, max = 32, message = "订单编号长度必须介于 1 和 32 之间")
	@ExcelField(title = "订单编号", align = 2, sort = 10)
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public List<OrderReturnsRef> getOrderReturnsRefList() {
		return orderReturnsRefList;
	}

	public void setOrderReturnsRefList(List<OrderReturnsRef> orderReturnsRefList) {
		this.orderReturnsRefList = orderReturnsRefList;
	}

	public List<OrderTableReturnsItem> getOrderTableReturnsItemList() {
		return orderTableReturnsItemList;
	}

	public void setOrderTableReturnsItemList(
			List<OrderTableReturnsItem> orderTableReturnsItemList) {
		this.orderTableReturnsItemList = orderTableReturnsItemList;
	}

	public WithdrawalApplyFor getWithdrawalApplyFor() {
		return withdrawalApplyFor;
	}

	public void setWithdrawalApplyFor(WithdrawalApplyFor withdrawalApplyFor) {
		this.withdrawalApplyFor = withdrawalApplyFor;
	}
	@ExcelField(title = "供应商名称", align = 2, sort = 20)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
}