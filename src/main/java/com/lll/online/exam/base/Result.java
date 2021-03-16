package com.lll.online.exam.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Target;

/**
 * 结果返回类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/15
 */
@Data
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String message;
    private T response;

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, T response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public static Result ok(){
        SystemCode ok = SystemCode.OK;
        return new Result(ok.getCode(),ok.getMessage());
    }

    public static<F> Result<F> ok(F data){
        SystemCode ok = SystemCode.OK;
        return new Result<>(ok.getCode(),ok.getMessage(),data);
    }

    public static Result fail(Integer code,String message){
        return new Result(code,message);
    }
}
