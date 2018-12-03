package com.wj.spc.demo_1203.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/test01",
            method = RequestMethod.GET)
    public String test01(){
        return "Welcome to the demo1203!";
    }

}
