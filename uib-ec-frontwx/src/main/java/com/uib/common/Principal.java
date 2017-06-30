
package com.uib.common;

import java.io.Serializable;

/**
 * 身份信息
 * @Title Principal
 * @Company: e-life
 * @Copyright: Copyright(C) 2014
 * @Version   1.0
 * @author kevin
 * @date 2014-6-10
 * @time 下午2:22:35
 * @Description
 */
public class Principal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private String id;

	/** 用户名 */
	private String username;

	/**
	 * @param id
	 *            ID
	 * @param username
	 *            用户名
	 */
	public Principal(String id, String username) {
		this.id = id;
		this.username = username;
	}

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置ID
	 * 
	 * @param id
	 *            ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取用户名
	 * 
	 * @return 用户名
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置用户名
	 * 
	 * @param username
	 *            用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return username;
	}

}