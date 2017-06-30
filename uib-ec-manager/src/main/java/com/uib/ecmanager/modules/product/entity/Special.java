/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.product.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 专题信息Entity
 * @author limy
 * @version 2016-07-14
 */
public class Special extends DataEntity<Special> {
	
	private static final long serialVersionUID = 1L;
	private String specialTitle;		// 专题名称
	private String specialArticle;		// 推荐内容
	private String productCount;		// 推荐商品数
	private String showImage;		// 展示图片
	private String sort;		// 排序
	private Date beginDate;		//  开始日期
	private Date endDate;		// 结束日期
	private String updateFlag;
	private Date after1BeginDate;
	private Date before1EndDate;
	private Date after2BeginDate;
	private Date before2EndDate;
	public Date getAfter1BeginDate() {
		return after1BeginDate;
	}

	public void setAfter1BeginDate(Date after1BeginDate) {
		this.after1BeginDate = after1BeginDate;
	}

	public Date getBefore1EndDate() {
		return before1EndDate;
	}

	public void setBefore1EndDate(Date before1EndDate) {
		this.before1EndDate = before1EndDate;
	}

	public Date getAfter2BeginDate() {
		return after2BeginDate;
	}

	public void setAfter2BeginDate(Date after2BeginDate) {
		this.after2BeginDate = after2BeginDate;
	}

	public Date getBefore2EndDate() {
		return before2EndDate;
	}

	public void setBefore2EndDate(Date before2EndDate) {
		this.before2EndDate = before2EndDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Special() {
		super();
	}

	public Special(String id){
		super(id);
	}

	@Length(min=0, max=255, message="专题名称长度必须介于 0 和 255 之间")
	public String getSpecialTitle() {
		return specialTitle;
	}

	public void setSpecialTitle(String specialTitle) {
		this.specialTitle = specialTitle;
	}
	
	@Length(min=0, max=255, message="推荐内容长度必须介于 0 和 255 之间")
	public String getSpecialArticle() {
		return specialArticle;
	}

	public void setSpecialArticle(String specialArticle) {
		this.specialArticle = specialArticle;
	}
	

	@Length(min=0, max=255, message="展示图片长度必须介于 0 和 255 之间")
	public String getShowImage() {
		return showImage;
	}

	public void setShowImage(String showImage) {
		this.showImage = showImage;
	}
	
	@Length(min=0, max=32, message="排序长度必须介于 0 和 32 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
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

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}
	
}