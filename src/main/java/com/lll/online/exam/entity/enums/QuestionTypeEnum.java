package com.lll.online.exam.entity.enums;

/**
 * Question类型枚举
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/25
 */
public enum  QuestionTypeEnum {

    SingleChoice(1, "单选题"),
    MultipleChoice(2, "多选题"),
    TrueFalse(3, "判断题"),
    GapFilling(4, "填空题"),
    ShortAnswer(5, "简答题");

    QuestionTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    int code;
    String name;


}
