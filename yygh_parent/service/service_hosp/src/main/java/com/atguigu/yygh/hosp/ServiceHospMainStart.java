package com.atguigu.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//只能扫描当前模块的注解
@ComponentScan(value = "com.atguigu.yygh")//扫描当前模块依赖的其他模块的注解
@MapperScan(value = "com/atguigu/yygh/hosp/mapper")
public class ServiceHospMainStart {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospMainStart.class, args);
    }
}
