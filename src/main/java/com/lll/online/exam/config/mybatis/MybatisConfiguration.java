package com.lll.online.exam.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 配置类
 *
 * @author ：Mr.Garlic
 * @date ： 2021/3/16
 */
@MapperScan("com.lll.online.exam.mapper")
@Configuration
public class MybatisConfiguration {
}
