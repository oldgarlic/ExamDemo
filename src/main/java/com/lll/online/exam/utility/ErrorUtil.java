package com.lll.online.exam.utility;

/**
 * 错误正文格式化工具类
 *
 * @author: Mr.Garlic
 * @date: 2021/3/21
 */
public class ErrorUtil {
    public static String parameterErrorFormat(String field, String msg) {
        return "【" + field + " : " + msg + "】";
    }
}