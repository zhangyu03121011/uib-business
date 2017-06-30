/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.ad.entity;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 广告管理Entity
 * @author gaven
 * @version 2015-06-06
 */
public class Advertisement extends DataEntity<Advertisement> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private Integer type;		// 类型
	private String adPositionId;		// 广告位
	private String path;		// 路径
	private String content;		// 内容
	private Integer orders;		// 排序
	private String url;		// 链接地址
	private String appPath;	//app展示图片路径
	private String appUrl;	//app链接地址
	private String wxPath; //微信展示图片路径
	private String wxUrl;	//微信链接地址
	private Date beginDate;		// 起始日期
	private Date endDate;		// 结束日期
	private AdvertisementPosition advertisementPosition;
	
	public Advertisement() {
		super();
	}

	public Advertisement(String id){
		super(id);
	}

	@Length(min=0, max=255, message="标题长度必须介于 0 和 255 之间")
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
	
	@Length(min=1, max=64, message="广告位长度必须介于 1 和 64 之间")
	public String getAdPositionId() {
		return adPositionId;
	}

	public void setAdPositionId(String adPositionId) {
		this.adPositionId = adPositionId;
	}
	
	@Length(min=0, max=255, message="路径长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="链接地址长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getWxPath() {
		return wxPath;
	}

	public void setWxPath(String wxPath) {
		this.wxPath = wxPath;
	}

	public String getWxUrl() {
		return wxUrl;
	}

	public void setWxUrl(String wxUrl) {
		this.wxUrl = wxUrl;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public AdvertisementPosition getAdvertisementPosition() {
		return advertisementPosition;
	}

	public void setAdvertisementPosition(AdvertisementPosition advertisementPosition) {
		this.advertisementPosition = advertisementPosition;
	}
	
}