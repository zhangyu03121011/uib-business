package com.uib.ad.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 广告管理Entity
 * 
 * @author gaven
 * @version 2015-06-06
 */
public class Advertisement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1771009710930673952L;

	// id
	private String id;

	private String title; // 标题
	private Integer type; // 类型
	private String adPositionId; // 广告位
	private String path; // 路径
	private String content; // 内容
	private Integer orders; // 排序
	private String url; // 链接地址
	private Timestamp beginDate; // 起始日期
	private Timestamp endDate; // 结束日期

	protected String remarks; // 备注
	protected String createBy; // 创建者
	protected Timestamp createDate; // 创建日期
	protected String updateBy; // 更新者
	protected Timestamp updateDate; // 更新日期
	protected String delFlag; // 删除标记（0：正常；1：删除；2：审核）

	private AdvertisementPosition advertisementPosition;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAdPositionId() {
		return adPositionId;
	}

	public void setAdPositionId(String adPositionId) {
		this.adPositionId = adPositionId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public AdvertisementPosition getAdvertisementPosition() {
		return advertisementPosition;
	}

	public void setAdvertisementPosition(
			AdvertisementPosition advertisementPosition) {
		this.advertisementPosition = advertisementPosition;
	}

}
