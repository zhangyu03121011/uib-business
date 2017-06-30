package com.uib.union.utils;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 打印请求接口参数
 * @author kevin
 *
 */
public class LogPrintUtil {

	private static final Logger logger = Logger.getLogger("uibLogger");

	public static void logParamMap(Map<String, String> requestMap) {
		for (Map.Entry<String, String> entry : requestMap.entrySet()) {
			logger.info("请求参数为: "+ entry.getKey() + "====> " + entry.getValue());
		}
	}
}
