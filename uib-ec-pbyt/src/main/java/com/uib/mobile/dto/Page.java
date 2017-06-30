/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.mobile.dto;

import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

import com.uib.serviceUtils.Utils;

/**
 * @ClassName: Page
 * @Description: 移动端分页工具类
 * @author sl
 * @date 2015年10月23日 上午9:53:12
 */
public class Page {

	public static void paging(Map<String, Object> param) {
		Object begin = param.get("begin");
		Object end = param.get("end");
		if (Utils.isObjectsBlank(begin, end) || !NumberUtils.isDigits(begin.toString()) || !NumberUtils.isDigits(end.toString())) {
			param.put("startSize", 0);
			param.put("currSize", 10);
			return;
		}
		Integer startSize = Integer.parseInt(begin.toString());
		Integer currSize = Integer.parseInt(end.toString());
		if (startSize < 0) {
			startSize = 0;
		}
		param.put("startSize", startSize);
		param.put("currSize", currSize - startSize);
	}
}
