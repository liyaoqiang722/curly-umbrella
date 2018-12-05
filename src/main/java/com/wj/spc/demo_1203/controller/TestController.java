package com.wj.spc.demo_1203.controller;

import com.wj.spc.demo_1203.domain.User;
import com.wj.spc.demo_1203.servuice.TestService;
import com.wj.spc.demo_1203.viewModel.Result;
import com.wj.spc.demo_1203.viewModel.SuccessResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping(value = "/test01",
            method = RequestMethod.GET)
    public String test01(){
        return "Welcome to the demo1203!";
    }


    @RequestMapping(value = "/test02",
            method = RequestMethod.GET)
    public Object test02(){
        List<User> user = testService.dataSource1();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);
        successResponse.setData(user);

        return successResponse;
    }

    @RequestMapping(value = "/test03",
            method = RequestMethod.GET)
    public Object test03(){
        User user = testService.dataSource2();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);
        successResponse.setData(user);

        return successResponse;
    }
}
