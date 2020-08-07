package com.spring.cloud.jk.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${username.frank}")
    private String name;
    @Value("${address.china}")
    private String address;

    @GetMapping(value = "/aa")
    public Object aa(){
        String s = name + "{}" + address;
        return s;
    }
}
