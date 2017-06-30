package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderTableReturnsDto implements Serializable{
	private static final long serialVersionUID = -8501497691035685164L;
	
	private String id;              //主键
	private String returnNo;        //退货单编号
	private String address;         //地址
	private String area;            //地区
	private String deliveryCorp;    //物流公司
	private String freight;         //物流费用
	private String operator;        //操作员
	private String phone;           //电话
	private String shipper;         //发货人
	private String shippingMethod;  //配送方式
	private String trackingNo;      //运单号
	private String zipCode;         //邮编
    private String orderNo;         //订单编号
	private String createBy;        //创建者
	private Date createDate;        //创建时间
	private String updateBy;        //更新者
	private Date updateDate;      //更新时间
	private String remarks;         //备注信息
	private String delFlag;         //删除标记（0：正常；1：删除）
	private String returnType;      //退货类型：（1退款 2退货 3换货）
	private String returnStatus;    //退货状态（1已处理 2无法退货 3未处理）
	private Date applyTime;       //申请时间
	private String userName;       //用户名
	private String productId;      //商品编号
	private String returnReson;    //退货原因
	private String returnSum;      //退款金额
	private String returnDesc;     //退货说明
	private String supplierId;     //供应商id
	
	/**   用于传递页面参,不参与持久化  **/
	private String name;                    //商品名称
	private Integer quantity;                //数量

	
	
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReturnNo() {
		return returnNo;
	}
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDeliveryCorp() {
		return deliveryCorp;
	}
	public void setDeliveryCorp(String deliveryCorp) {
		this.deliveryCorp = deliveryCorp;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShipper() {
		return shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public String getTrackingNo() {
		return trackingNo;
	}
	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getReturnReson() {
		return returnReson;
	}
	public void setReturnReson(String returnReson) {
		this.returnReson = returnReson;
	}
	public String getReturnSum() {
		return returnSum;
	}
	public void setReturnSum(String returnSum) {
		this.returnSum = returnSum;
	}
	public String getReturnDesc() {
		return returnDesc;
	}
	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}
}
