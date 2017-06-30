package com.uib.ptyt.entity;

import java.io.Serializable;
import java.util.Date;

public class StoreMerchantDto implements Serializable {

	private static final long serialVersionUID = 3645974582755551638L;

	private String id;
	private String storeId;
	private String merchantId;
	private String createBy;
	private Date createTime;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
