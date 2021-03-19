package com.lll.online.exam.config.spring.security;

import com.lll.online.exam.base.Result;
import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.utility.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * response处理工具类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/15
 */
public class RestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RestUtil.class);

    public static void response(HttpServletResponse response, SystemCode systemCode){
        response(response,systemCode.getCode(),systemCode.getMessage(),null);
    }

    public static void response(HttpServletResponse response,Integer code,String message){
        response(response,code,message,null);
    }

    public static void response(HttpServletResponse response,SystemCode systemCode,Object content){
        response(response,systemCode.getCode(),systemCode.getMessage(),content);
    }

    public static void response(HttpServletResponse response,Integer code,String message,Object content){
        try {
            Result<Object> result = new Result<>(code, message, content);
            // 实体类转化为JsonStr
            String jsonStr = JsonUtil.toJsonStr(result);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            logger.info("RestUtil工具类出现异常-----------------------------------");
        }
    }
}
