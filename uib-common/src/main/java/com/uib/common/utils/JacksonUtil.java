package com.uib.common.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

/**
 * jackson工具类
 * 
 * @Title JacksonUtil
 * @Company: uib
 * @Copyright: Copyright(C) 2015
 * @Version 1.0
 * @author wanghuan
 * @date 2015年02月11日
 * @time 下午3:21:48
 * @Description
 */
public class JacksonUtil {
	/**
	 * 对象序列化成json
	 * 
	 * @Title writeValueAsString
	 * @author wanghuan
	 * @param
	 * @return String
	 * @throws
	 * @date 2013年11月26日
	 * @time 下午3:22:34
	 * @Description
	 */
	public static String writeValueAsString(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			result = mapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 对象带日期格式列化成json
	 * 
	 * @Title writeValueAsStringWithDateFormat
	 * @author wanghuan
	 * @param
	 * @return String
	 * @throws
	 * @date 2013年11月26日
	 * @time 下午3:22:45
	 * @Description
	 */
	public static String writeValueAsStringWithDateFormat(Object o, DateFormat formatter) {
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		mapper.setDateFormat(formatter);
		try {
			result = mapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * json反序列化成对象
	 * 
	 * @Title readValue
	 * @author wanghuan
	 * @param
	 * @return T
	 * @throws
	 * @date 2013年11月26日
	 * @time 下午3:22:54
	 * @Description
	 */
	public static <T> T readValue(String json, Class<?> clas) throws JsonParseException, JsonMappingException,
			IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(sdf);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		return (T) mapper.readValue(json, clas);
	}

	public static <T> T readValueList(String json, JavaType javaType) throws JsonParseException, JsonMappingException,
			IOException {
		/*
		 * 调用代码 JavaType javaType =
		 * JacksonUtil.getCollectionType(ArrayList.class, ReadyLcEntity.class);
		 * List<ReadyLcEntity> entity =
		 * (List<ReadyLcEntity>)JacksonUtil.readValueList(result, javaType);
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(sdf);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		return (T) mapper.readValue(json, javaType);
	}
	
	
	

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}
