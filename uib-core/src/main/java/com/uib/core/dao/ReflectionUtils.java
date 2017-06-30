package com.uib.core.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射工具类
 * @Title:    ReflectionUtils
 * Company:   wanghuan
 * Copyright: Copyright(C) 2014
 * @Version   1.0
 * @author    wanghuan
 * @date:     2012-12-6
 * @time:     下午04:06:00
 * Description:
 */
public class ReflectionUtils {
	
	/**
	 * java反射
	 * @Title:    getParameterizedTypeByParent
	 * @author    wanghuan
	 * @param     clazz
	 * @return    
	 * @throws    
	 * @date:     2012-12-6
	 * @time:     下午04:11:19
	 * Description:
	 */
	@SuppressWarnings("unchecked")
	public static Class getParameterizedTypeByParent(Class clazz) {
		Type tp = clazz.getGenericSuperclass();
		if (tp instanceof ParameterizedType) {
			return (Class) ((ParameterizedType) tp).getActualTypeArguments()[0];
		} else {
			throw new ClassCastException("this is not ParameterizedType type");
		}
	}
	
	/**
	 * 根据属性名获取对象的指定属性
	 * @Title getField
	 * @author wanghuan
	 * @param 
	 * @return Field
	 * @throws 
	 * @date 2014年11月06日
	 * @time 下午4:32:13
	 * @Description
	 */
	public static Field getField(Object obj, String fieldName) {
		Field field = null;
		
		for (Class<?> cls = obj.getClass(); cls != Object.class; cls = cls.getSuperclass()) {
			try {
				field = cls.getDeclaredField(fieldName);
			} catch (Exception e) {
				
			}
		}
		
		return field;
	}
	
	/**
	 * 获取对象指定属性的值
	 * @Title getFieldValue
	 * @author wanghuan
	 * @param 
	 * @return Object
	 * @throws 
	 * @date 2014年11月06日
	 * @time 下午4:57:07
	 * @Description
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Object value = null;
		Field field = getField(obj, fieldName);
		try {
			if(field != null) {
				field.setAccessible(true); //设置对私有属性的访问权限
				value = field.get(obj);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	/**
	 * 给对象的属性赋值
	 * @Title setFieldValue
	 * @author wanghuan
	 * @param 
	 * @return Object
	 * @throws 
	 * @date 2014年11月6日
	 * @time 下午6:17:43
	 * @Description
	 */
	public static void setFieldValue(Object obj, String fieldName, Object value) {
		Field field = getField(obj, fieldName);
		try {
			if(field != null) {
				field.setAccessible(true); //设置对私有属性的访问权限
				field.set(obj, value);
				field.setAccessible(false);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
