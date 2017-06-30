/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.ad.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 广告位Entity
 * @author gaven
 * @version 2015-06-06
 */
public class AdvertisementPosition extends DataEntity<AdvertisementPosition> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String code;		// 广告位编号
	private Integer height;		// 高度
	private Integer width;		// 宽度
	private String description;		// 描述
	private String template;		// 模板
	
	public AdvertisementPosition() {
		super();
	}

	public AdvertisementPosition(String id){
		super(id);
	}
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min=1, max=255, message="名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="高度不能为空")
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	@NotNull(message="宽度不能为空")
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	
	@Length(min=0, max=255, message="描述长度必须介于 0 和 255 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
}