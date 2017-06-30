package com.uib.ptyt.entity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 商品专题
 * 
 * @author chengjian
 * @version 2016-07-26
 */
public class Special implements Serializable {
	private static final long serialVersionUID = -1771009710930673952L;
	
	private String id;
	private String specialTitle;                    //专题名称
	private String specialArticle;                 //推荐内容
	private String specialProductCount;           //推荐商品数
	private String showImage;                      //展示图片
	private String sort;                            //排序
	private String createDate;                     //创建时间
	private String beginDate;                      //开始日期
	private String endDate;                        //结束日期

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSpecialTitle() {
		return specialTitle;
	}

	public void setSpecialTitle(String specialTitle) {
		this.specialTitle = specialTitle;
	}

	public String getSpecialArticle() {
		return specialArticle;
	}

	public void setSpecialArticle(String specialArticle) {
		this.specialArticle = specialArticle;
	}

	public String getSpecialProductCount() {
		return specialProductCount;
	}

	public void setSpecialProductCount(String specialProductCount) {
		this.specialProductCount = specialProductCount;
	}

	public String getShowImage() {
		return showImage;
	}

	public void setShowImage(String showImage) {
		this.showImage = showImage;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}
