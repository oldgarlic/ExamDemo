package com.lll.online.exam.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 以ObjectMapper为基础的JSON工具类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/15
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 对象转Json字符串
     */
    public static <T> String toJsonStr(T t){
        try {
            return MAPPER.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            logger.info("对象转JSON字符串出现报错--------------------------------------------");
        }
        return null;
    }

    /**
     * 根据InputStream和Class生成对象
     */
    public static <T> T toJsonObject(InputStream stream,Class<T> value){
        try {
            T t = MAPPER.readValue(stream, value);
            return t;
        } catch (IOException e) {
            logger.info("input转对象失败--------------------------------------------------------");
        }
        return null;
    }

    /*
    * @Description: 字符串转对象
    * @Date: 2021/3/25
    */
    public static <T> T toJsonObject(String json, Class<T> valueType) {
        try {
            return MAPPER.<T>readValue(json, valueType);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /*
    * @Description: 字符串转List<>
    * @Date: 2021/3/27
    */
    public static <T> List<T> toJsonListObject(String json, Class<T> valueType) {
        try {
            JavaType getCollectionType = MAPPER.getTypeFactory().constructParametricType(List.class, valueType);
            List<T> list = MAPPER.readValue(json, getCollectionType);
            return list;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
