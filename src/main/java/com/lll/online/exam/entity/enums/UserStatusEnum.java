package com.lll.online.exam.entity.enums;

/**
 * 用户状态枚举：数据保存的是Integer，前端需要的是String
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
public enum  UserStatusEnum {
    /**
     *
     */
    ENABLE(1,"可用"),
    DISABLE(2,"禁用");

    int code;
    String status;

    UserStatusEnum(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
