package com.cc.springdemo.controller;

import java.util.HashMap;
import java.util.Map;

import com.cc.springdemo.utils.WebResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring-boot-demo-2-1
 *
 */
@RestController
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping
    public String index() {
        return "hello world";
    }

    // @RequestParam 简单类型的绑定，可以出来get和post
    @RequestMapping(value = "/index/get")
    public HashMap<String, Object> get(@RequestParam String name) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("title", "hello world");
        map.put("name", name);
        return map;
    }


    // @RequestParam 简单类型的绑定，可以出来get和post
    @RequestMapping(value = "/443")
    public Map<String, Object> notLogin() {
        Map<String, Object> ret = new HashMap<>();
        Map<String, Object> response = WebResponse.getFailResponse("未登录");
        return response;
    }
}

