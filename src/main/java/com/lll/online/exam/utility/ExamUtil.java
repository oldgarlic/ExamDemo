package com.lll.online.exam.utility;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static Integer scoreFromVM(String score) {
        if (score == null) {
            return null;
        } else {
            return (int) (Float.parseFloat(score) * 10);
        }
    }

    private static final String ANSWER_SPLIT = ",";

    public static String contentToString(List<String> contentArray) {
        return contentArray.stream().sorted().collect(Collectors.joining(ANSWER_SPLIT));
    }

    public static List<String> contentToArray(String contentArray) {
        return Arrays.asList(contentArray.split(ANSWER_SPLIT));
    }
}
