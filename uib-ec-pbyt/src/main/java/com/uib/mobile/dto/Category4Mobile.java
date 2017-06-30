package com.uib.mobile.dto;

import java.util.List;

/**
 * 
 * @author gaven
 *
 */
public class Category4Mobile {
	private String id; // 分类id
	private String name; // 分类名称
	private String imagePath; // 分类图片路径
	private String parentId; // 分类父类id
	private List<Category4Mobile> subCategorys; // 子类集合

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Category4Mobile> getSubCategorys() {
		return subCategorys;
	}

	public void setSubCategorys(List<Category4Mobile> subCategorys) {
		this.subCategorys = subCategorys;
	}

}
