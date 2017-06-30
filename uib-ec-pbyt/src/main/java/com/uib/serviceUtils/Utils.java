package com.uib.serviceUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.uib.mobile.dto.CartPojo4Mobile;

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
	public static boolean isAllBlank(Object... values) {
		if (Utils.isBlank(values)) {
			return true;
		}
		for (Object value : values) {
			if (Utils.isNotBlank(value)) {
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
		String price = setScale(new BigDecimal(amount), roundType, priceScale)
				.toString();
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

	/**
	 * 检查对象字段为空模式
	 * 
	 * @author gaven
	 *
	 */
	public enum FieldCheckModel {
		/**
		 * 除此之外
		 */
		BESIDES,
		/**
		 * 包含
		 */
		CONTAINS,
		/**
		 * 所有
		 */
		ALL;
	}

	/**
	 * 判断对象字段是否为空
	 * 
	 * @param bean
	 * @return 为空字段
	 * @throws Exception
	 */
	public static String checkFieldValueNull(Object bean) throws Exception {
		String message = "";
		if (bean == null) {
			message = "object";
		} else {
			for (Field f : bean.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (Utils.isBlank(f.get(bean))) { // 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
					message = f.getName();
					break;
				}
			}
		}
		return message;
	}

	/**
	 * 检查对象字段是否为空
	 * 
	 * @param bean
	 *            对象
	 * @param checkModel
	 *            检查模式
	 * @param filedNames
	 *            属性名数组
	 * @return 为空字段,都不为空返回null
	 * @throws Exception
	 */
	public static String checkFieldsValueNull(Object bean,
			FieldCheckModel checkModel, String... filedNames) throws Exception {
		String message = null;
		if (bean == null) {
			message = "object";
			return message;
		}
		for (Field f : bean.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			message = checkField(bean, f);
			if (Utils.isBlank(message)) {
				continue;
			}
			if (!Utils.isObjectsBlank(checkModel, filedNames)) {
				boolean flag = false;
				switch (checkModel) {
				case BESIDES:
					if (!(Arrays.binarySearch(filedNames, f.getName()) >= 0)) {
						flag = true;
					}
					break;
				case CONTAINS:
					if (Arrays.binarySearch(filedNames, f.getName()) >= 0) {
						flag = true;
					}
					break;
				default:
					flag = true;
					break;
				}
				if (flag) {
					break;
				}
			} else {
				if (Utils.isNotBlank(message)) {
					break;
				}
			}
		}
		return message;
	}

	/**
	 * 检查对象某属性是否为空
	 * 
	 * @param bean
	 * @param f
	 * @return 为空字段,不为空返回null
	 * @throws Exception
	 */
	public static String checkField(Object bean, Field f) throws Exception {
		if (Utils.isBlank(f.get(bean))) { // 判断字段是否为空，并且对象属性中的基本都会转为对象类型来判断
			return f.getName();
		}
		return null;
	}

	/**
	 * 检查对象除了某些字段外的其他属性是否为空
	 * 
	 * @param bean
	 * @param filedNames
	 * @return 为空字段,都不为空返回null
	 * @throws Exception
	 */
	public static String checkFieldValueNullBesides(Object bean,
			String... filedNames) throws Exception {
		return checkFieldsValueNull(bean, FieldCheckModel.BESIDES, filedNames);
	}

	/**
	 * 检查对象某些字段是否为空
	 * 
	 * @param bean
	 * @param filedNames
	 * @return 为空字段,都不为空返回null
	 * @throws Exception
	 */
	public static String checkFieldValueNullContains(Object bean,
			String... filedNames) throws Exception {
		return checkFieldsValueNull(bean, FieldCheckModel.CONTAINS, filedNames);
	}

	public static void main(String[] args) throws Exception {
		CartPojo4Mobile cart = new CartPojo4Mobile();
		System.out.println(checkFieldValueNull(cart));
	}
	
	/**
	 * bean校验
	 * 
	 * @param t
	 * @throws ValidationException
	 */
	public static <T>  String validate(T t) throws ValidationException {
        ValidatorFactory vFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = vFactory.getValidator();
        Set<ConstraintViolation<T>> set =  validator.validate(t);
        StringBuilder validateError = new StringBuilder();
        if(set.size()>0){
            for(ConstraintViolation<T> val : set){
                validateError.append(val.getMessage());
            }
        }
        return validateError.toString();
    }
}
