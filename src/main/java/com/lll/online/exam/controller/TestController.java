package com.lll.online.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/9
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "hello,world";
    }
}
