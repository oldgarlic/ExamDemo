package com.lll.online.exam.entity.enums;

import java.util.HashMap;
import java.util.Map;

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

    public static boolean needSaveTextContent(Integer code) {
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.getQuestionTypeEnum(code);
        switch (questionTypeEnum) {
            case GapFilling:
            case ShortAnswer:
                return true;
            default:
                return false;
        }
    }

    public static final HashMap<Integer,QuestionTypeEnum> MAP = new HashMap<>();

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static QuestionTypeEnum getQuestionTypeEnum(Integer code){

        return MAP.get(code);
    }

    static {
        for (QuestionTypeEnum item : QuestionTypeEnum.values()) {
            MAP.put(item.getCode(),item);
        }
    }
}
