package com.kindlepocket.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kindlepocket.web.controller.KindlePocketController;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(KindlePocketController.class, args);
    }

}
