package com.uib.ecmanager.common.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 提供 JSON 相关实用方法
 * @author gaven
 *
 */
public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper JSON_OBJECT_MAPPER;
    static {
        JSON_OBJECT_MAPPER = new ObjectMapper();
    }

    /**
     * 序列化对象为 JSON 字符串
     * @param obj
     * @return
     */
    public static String toJSON(Object obj) {
        try {
            return JSON_OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception ex) {
            LOGGER.error("序列化对象为 JSON 字符串时出现错误！", ex);
            return null;
        }
    }

    /**
     * 反序列化 JSON 字符串为指定类型的对象
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseJSON(String json, Class<T> clazz) {
        try {
            return JSON_OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception ex) {
            LOGGER.error("反序列化 JSON 字符串为指定类型的对象时出现错误！", ex);
            return null;
        }
    }

}
