package com.lll.online.exam.utility;

import sun.java2d.pipe.SpanShapeRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类：把Date格式转换为String，减少前端处理
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
public class DateTimeUtil {
    /**
     * 指南：：：：
     * yyyy：年
     * MM：月
     * dd：日
     * hh：1~12小时制(1-12)
     * HH：24小时制(0-23)
     * mm：分
     * ss：秒
     */
    public static final String NORMAL_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_FORMAT = "yyyy-MM-dd HH:mm:ss";



    public static String dateFormat(Date date){
        if(date==null){return "";}
        SimpleDateFormat dateFormat = new SimpleDateFormat(NORMAL_FORMAT);
        return dateFormat.format(date);
    }

    public static String dateShortFormat(Date date){
        if(date==null){return null;}
        SimpleDateFormat dateFormat = new SimpleDateFormat(SHORT_FORMAT);
        return dateFormat.format(date);
    }
}
