/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.navigation.entity;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.Length;

/**
 * 导航管理Entity
 * 
 * @author gaven
 * @version 2015-06-08
 */
public class Navigation {

	private String name; // 名称
	private String url; // 链接地址
	private Integer position; // 位置
	private Integer tag; // 所属标签
	private Integer isBlankTarget; // 是否新窗口打开
	private Integer orders; // 排序

	// 创建者
	private String createBy;
	// 创建时间
	private Timestamp createDate;
	// 更新者
	private String updateBy;
	// 更新时间
	private Timestamp updateDate;
	// 备注信息
	private String remarks;
	// 删除标记（0：正常；1：删除）
	private String delFlag;

	@Length(min = 0, max = 255, message = "名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 255, message = "链接地址长度必须介于 0 和 255 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Integer getIsBlankTarget() {
		return isBlankTarget;
	}

	public void setIsBlankTarget(Integer isBlankTarget) {
		this.isBlankTarget = isBlankTarget;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	/**
	 * 所属标签
	 */
	public enum Tags {

		/** 购物指南 */
		shoppingGuide("购物指南", 0),

		/** 配送方法 */
		distributeMethod("配送方法", 1),

		/** 支付方式 */
		paymentMethod("支付方式", 2),

		/** 售后服务 */
		afterSalesService("售后服务", 3),

		/** 购物流程 */
		shoppingProcess("购物流程", 4),

		/** 关于我们 */
		aboutUs("关于我们", 5),

		/** 帮助中心 */
		helpCenter("帮助中心 ", 6);

		// 成员变量
		private String description;
		private int index;

		private Tags(String description, int index) {
			this.description = description;
			this.index = index;
		}

		public String getDescription(int index) {
			for (Tags t : Tags.values()) {
				if (t.getIndex() == index) {
					return t.description;
				}
			}
			return null;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

	}

	/**
	 * 位置
	 */
	public enum Position {

		/** 顶部 */
		top("顶部", 0),

		/** 中间 */
		middle("中间", 1),

		/** 底部 */
		bottom("底部", 2),

		/** 买家保障 */
		buyerProtection("买家保障 ", 3);
		// 成员变量
		private String name;
		private int index;

		private Position(String name, int index) {
			this.name = name;
			this.index = index;
		}

		// 普通方法
		public static String getName(int index) {
			for (Position p : Position.values()) {
				if (p.getIndex() == index) {
					return p.name;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

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

}