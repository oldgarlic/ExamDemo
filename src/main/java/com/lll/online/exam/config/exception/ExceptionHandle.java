package com.lll.online.exam.config.exception;

import com.lll.online.exam.base.Result;
import com.lll.online.exam.base.SystemCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
