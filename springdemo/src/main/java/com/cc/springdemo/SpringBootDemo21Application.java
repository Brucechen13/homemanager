package com.cc.springdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemo21Application {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootDemo21Application.class);

    public static void main(String[] args) {
        logger.info("start application!!");
        SpringApplication.run(SpringBootDemo21Application.class, args);
    }
}
