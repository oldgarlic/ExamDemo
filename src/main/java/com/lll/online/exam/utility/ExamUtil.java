package com.lll.online.exam.utility;

/**
 * Exam工具类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/25
 */
public class ExamUtil {

    public static String scoreToVM(Integer score) {
        if (score % 10 == 0) {
            return String.valueOf(score / 10);
        } else {
            return String.format("%.1f", score / 10.0);
        }
    }
}
