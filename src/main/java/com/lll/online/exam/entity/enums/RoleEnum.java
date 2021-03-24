package com.lll.online.exam.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色枚举：数据保存的是Integer，SpringSecurity需要的是String
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public enum  RoleEnum {
    /**
     * 角色信息
     */
    STUDENT(1,"STUDENT"),
    ADMIN(3,"ADMIN");


    Integer code;
    String name;

    RoleEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<Integer,RoleEnum> map = new HashMap<>();

    static {
        for (RoleEnum value : RoleEnum.values()) {
            map.put(value.getCode(),value);
        }
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RoleEnum getRoleEnum(Integer code){
        RoleEnum roleEnum = map.get(code);
        return roleEnum;
    }


    public String getRoleName(){
        return "ROLE_" + name;
    }
}
