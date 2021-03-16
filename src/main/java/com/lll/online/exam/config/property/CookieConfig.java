package com.lll.online.exam.config.property;

/**
 * Cookie配置
 * RememberMeService生成cookie用到的key和过期时间
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public class CookieConfig {
    public static String getName(){
        return "lll";
    }

    public static Integer getInterval() {
        return 30 * 24 * 60 * 60;
    }
}
