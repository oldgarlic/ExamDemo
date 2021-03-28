package com.lll.online.exam.utility;

import sun.java2d.pipe.SpanShapeRenderer;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public static final String SHORT_FORMAT = "yyyy-MM-dd";



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
    /*
    * @Description:获取当前最后一天最后的时间，例如 2021-03-31 23:59:59
    * @Date: 2021/3/22
    */
    public static Date getMonthEndDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        Calendar calendar = Calendar.getInstance();
        // 月份+1
        calendar.add(Calendar.MONTH,1);
        // 天数设置为第0天
        calendar.set(Calendar.DAY_OF_MONTH,0);
        Date time = calendar.getTime();
        String format = dateFormat.format(time);
        return parse(NORMAL_FORMAT,format);
    }

    /*
    * @Description: 获取当月开始时间
    * @Date: 2021/3/22
    */
    public static Date getMonthStartDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, 0);
        instance.set(Calendar.DAY_OF_MONTH,1);
        String format = dateFormat.format(instance.getTime());
        return parse(NORMAL_FORMAT,format);
    }


    /*
    * @Description: 日期字符串解析为Date对象
    * @Date: 2021/3/22
    */
    public static Date parse(String normalFormat, String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(normalFormat);
        try {
          return  dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /*
    * @Description: 如2月，获取1.2.3.、、、31的List<String>
    * @Date: 2021/3/22
    */
    public static List<String> currentMonthDay(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getMonthEndDate());
        int count = calendar.get(Calendar.DAY_OF_MONTH);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(String.valueOf(i+1));
        }
        return list;
    }

    public static List<String> getStartToNowDate(){
        // ok
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        int size = instance.get(Calendar.DAY_OF_MONTH);
        ArrayList<String> list = new ArrayList<>(size);

        //  ok
        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(getMonthStartDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat(SHORT_FORMAT);

        for (int i = 0; i < size; i++) {
            list.add(dateFormat.format(startCalendar.getTime()));
            startCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }




    public static void main(String[] args) {
    }
}
