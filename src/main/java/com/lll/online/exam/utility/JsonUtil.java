package com.lll.online.exam.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

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
}
