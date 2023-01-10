package com.orange.mo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.orange.**")
public class OrangeMoApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrangeMoApplication.class, args);
    }
}
