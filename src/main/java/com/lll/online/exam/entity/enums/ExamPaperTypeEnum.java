package com.lll.online.exam.entity.enums;

/**
 * 试卷类型枚举
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/27
 */
public enum ExamPaperTypeEnum {
    Fixed(1, "固定试卷"),
    TimeLimit(4,"时段试卷"),
    Task(6, "任务试卷");;

    private Integer code;
    private String name;

    ExamPaperTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }}
