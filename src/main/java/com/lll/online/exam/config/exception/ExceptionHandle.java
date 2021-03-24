package com.lll.online.exam.config.exception;

import com.lll.online.exam.base.Result;
import com.lll.online.exam.base.SystemCode;
import com.lll.online.exam.utility.ErrorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * 异常捕获取
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/20
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handle(Exception e){
        logger.info(e.getMessage(),e);
        return new Result(SystemCode.InnerError.getCode(),SystemCode.InnerError.getMessage());
    }

    /*
     * @Description: 捕获validation验证失败抛出的异常
     * @Param: MethodArgumentNotValidException
     * @return: com.lll.online.exam.base.Result
     * @Date: 2021/3/21
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handler(MethodArgumentNotValidException e) {
        // TODO：待了解
        String errorMsg = e.getBindingResult().getAllErrors().stream().map(file -> {
            FieldError fieldError = (FieldError) file;
            return ErrorUtil.parameterErrorFormat(fieldError.getField(), fieldError.getDefaultMessage());
        }).collect(Collectors.joining());

        return new Result(SystemCode.ParameterValidError.getCode(), errorMsg);
    }

    /*
     * @Description: 捕获前端与后面数据转换失败抛出的异常
     * @Param: BindException
     * @return: com.lll.online.exam.base.Result
     * @Date: 2021/3/21
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result handler(BindException e) {
        // TODO：待了解
        String errorMsg = e.getBindingResult().getAllErrors().stream().map(file -> {
            FieldError fieldError = (FieldError) file;
            return ErrorUtil.parameterErrorFormat(fieldError.getField(), fieldError.getDefaultMessage());
        }).collect(Collectors.joining());
        return new Result<>(SystemCode.ParameterValidError.getCode(), errorMsg);
    }
}
