package com.project.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ShoppingApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShoppingApplication.class, args);
    }
}
