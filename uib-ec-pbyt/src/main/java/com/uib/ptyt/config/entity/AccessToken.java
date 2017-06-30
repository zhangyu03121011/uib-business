package com.uib.ptyt.config.entity;

/**
 * @Todo
 * @date 2016年6月14日
 * @author Ly
 */
public class AccessToken {

	private String token;
	private int expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
