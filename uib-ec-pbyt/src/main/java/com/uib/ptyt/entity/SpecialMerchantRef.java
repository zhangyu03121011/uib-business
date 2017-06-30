package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户和专题的关系表
 * 
 * @author chengjian
 * @version 2016-07-26
 */
public class SpecialMerchantRef implements Serializable {
	private static final long serialVersionUID = -1771009710930673952L;
	
	private String id;
	private String specialId;                  //专题编号
	private String merchantId;                 //商户号
	private Date createDate;                  //创建时间

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	
}
