package com.lll.online.exam.base;

/**
 * 系统状态类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/15
 */
public enum  SystemCode {
    /**
     * 成功
     */
    OK(1, "成功"),
    AccessTokenError(400, "用户登录令牌失效"),
    UNAUTHORIZED(401, "用户未登录"),
    AuthError(402, "用户名或密码错误"),
    InnerError(500, "系统内部错误"),
    ParameterValidError(501, "参数验证错误"),
    AccessDenied(502,"用户没有权限访问");

    int code;
    String message;

    SystemCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
