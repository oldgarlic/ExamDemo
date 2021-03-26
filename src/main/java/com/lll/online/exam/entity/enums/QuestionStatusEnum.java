package com.lll.online.exam.entity.enums;

/**
 * Question 状态类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/26
 */
public enum  QuestionStatusEnum {

    OK(1, "正常"),
    Publish(2, "发布");

    int code;
    String name;

    QuestionStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }

}
