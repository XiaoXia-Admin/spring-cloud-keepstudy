package com.kuang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.kuang.springcloud.mapper")
public class SpringCloudWxLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudWxLoginApplication.class, args);
    }
}
