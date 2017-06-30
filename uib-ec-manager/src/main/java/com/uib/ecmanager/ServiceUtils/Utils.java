package com.uib.ecmanager.ServiceUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.uib.common.utils.RandomUtil;
import com.uib.ecmanager.common.utils.StringUtils;

public class Utils {

	public static boolean isBlank(Object obj) {
		if (obj instanceof String) {
			return StringUtils.isBlank((String) obj);
		}
		if (obj instanceof Map) {
			return MapUtils.isEmpty((Map) obj);
		}
		if (obj instanceof Collection) {
			return CollectionUtils.isEmpty((Collection) obj);
		}
		if (obj instanceof Object[]) {
			return ((Object[]) obj).length == 0 ? true : false;
		}
		return obj == null ? true : false;
	}

	public static Boolean isNotBlank(Object obj) {
		return !isBlank(obj);
	}

	/**
	 * 判断所传对象数组是否存在为空
	 * 
	 * @param values
	 * @return
	 */
	public static boolean isObjectsBlank(Object... values) {
		if (Utils.isBlank(values)) {
			return true;
		}
		for (Object value : values) {
			if (Utils.isBlank(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断所传对象是否都为空
	 * 
	 * @param values
	 * @return
	 */
	public static boolean isAllBlank(Object... values){
		if(Utils.isBlank(values)){
			return true;
		}
		for(Object value:values){
			if(Utils.isNotBlank(value)){
				return false;
			}
		}
		return true;
	}
	
	public static boolean booleanOf(Object value) {
		if (isBlank(value)) {
			return false;
		}
		if (value instanceof String) {
			return (((String) value).matches("[1-9][0-9]*") || Boolean
					.valueOf((String) value));
		}
		if (value instanceof Number) {
			return ((Number) value).shortValue() > 0;
		}
		if (value instanceof Character) {
			int code = (int) ((char) value);
			return code > 48 && code < 60;
		}
		return false;
	}

	public static boolean strictBooleanOf(Object value) {
		if (value instanceof String) {
			return Boolean.valueOf((String) value);
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		return false;
	}

	/**
	 * 
	 * @param amount
	 *            数值
	 * @param roundType
	 *            精确类型
	 * @param priceScale
	 *            精度
	 * @return
	 */
	public BigDecimal setScale(BigDecimal amount, RoundType roundType,
			Integer priceScale) {
		if (amount == null) {
			return null;
		}
		int roundingMode;
		if (roundType == RoundType.roundUp) {
			roundingMode = BigDecimal.ROUND_UP;
		} else if (roundType == RoundType.roundDown) {
			roundingMode = BigDecimal.ROUND_DOWN;
		} else {
			roundingMode = BigDecimal.ROUND_HALF_UP;
		}
		return amount.setScale(priceScale, roundingMode);
	}

	/**
	 * 货币格式化
	 * 
	 * @param amount
	 *            金额
	 * @param showSign
	 *            显示标志
	 * @param showUnit
	 *            显示单位
	 * @return 货币格式化
	 */
	public static String currency(double amount, RoundType roundType,
			int priceScale, String currencySign, String currencyUnit) {
		String price = setScale(new BigDecimal(amount), roundType, priceScale).toString();
		if (isNotBlank(currencySign)) {
			price = currencySign + price;
		}
		if (isNotBlank(currencyUnit)) {
			price += currencyUnit;
		}
		return price;
	}

	/**
	 * 设置精度
	 * 
	 * @param amount
	 *            数值
	 * @return 数值
	 */
	public static BigDecimal setScale(BigDecimal amount, RoundType roundType,
			int priceScale) {
		if (amount == null) {
			return null;
		}
		int roundingMode;
		if (roundType == RoundType.roundUp) {
			roundingMode = BigDecimal.ROUND_UP;
		} else if (roundType == RoundType.roundDown) {
			roundingMode = BigDecimal.ROUND_DOWN;
		} else {
			roundingMode = BigDecimal.ROUND_HALF_UP;
		}
		return amount.setScale(priceScale, roundingMode);
	}

	/**
	 * 小数位精确方式
	 */
	public enum RoundType {

		/** 四舍五入 */
		roundHalfUp,

		/** 向上取整 */
		roundUp,

		/** 向下取整 */
		roundDown
	}
	
	public static String generatorUUIDNumber(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		String newDate =  sdf.format(date);
		String orderNo = newDate + RandomUtil.getRandom(5);
		return orderNo;
	}
}
