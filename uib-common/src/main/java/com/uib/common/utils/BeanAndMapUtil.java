/**
 * 
 */
package com.uib.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * bean和map互转的工具类
 * @Title BeanAndMapUtil
 * @Company: uib
 * @Copyright: Copyright(C) 2014
 * @Version   1.0
 * @author wanghuan
 * @date 2014年11月03日
 * @time 上午10:16:27
 * @Description
 */
public class BeanAndMapUtil {

	
	private static final Log logger = LogFactory.getLog("rootLogger");
	
	/**
	 * 将JavaBean对象转换成Map
	 * @Title transBean2Map
	 * @author wanghuan
	 * @param 
	 * @return Map<String,Object>
	 * @throws 
	 * @date 2014年11月03日
	 * @time 下午3:22:13
	 * @Description
	 */
	public static Map<String, Object> transBean2Map(Object obj) {  
		  
        if(obj == null){  
            return new HashMap<String, Object>();  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性   
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法   
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
            logger.error("transBean2Map 方法出错", e);
            e.printStackTrace();
        }  
        return map;  
    }  

	
	
	/**
	 * 将JavaBean对象转换成Map
	 * @Title transBean2Map
	 * @author wanghuan
	 * @param 
	 * @return Map<String,Object>
	 * @throws 
	 * @date 2014年11月03日
	 * @time 下午3:22:13
	 * @Description
	 */
	public static Map<String, String> transBean2MapString(Object obj) {  
		  
        if(obj == null){  
            return new HashMap<String, String>();  
        }          
        Map<String, String> map = new HashMap<String, String>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性   
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法   
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj); 
                    String valueStr = "";
                    if (null != value){
                    	valueStr = value.toString();
                    }
                    map.put(key, valueStr);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
            logger.error("transBean2Map 方法出错", e);
            e.printStackTrace();
        }  
        return map;  
    }
}
