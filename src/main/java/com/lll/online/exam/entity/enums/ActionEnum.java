package com.lll.online.exam.entity.enums;

/**
 * 动作枚举
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/27
 */
public enum  ActionEnum {
    ADD(1,"添加"),
    EDIT(2,"修改");
    private Integer code;
    private String name;

    ActionEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }}
