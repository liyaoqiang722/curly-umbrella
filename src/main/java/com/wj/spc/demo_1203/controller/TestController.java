package com.wj.spc.demo_1203.controller;

import com.wj.spc.demo_1203.domain.Province;
import com.wj.spc.demo_1203.domain.User;
import com.wj.spc.demo_1203.servuice.TestService;
import com.wj.spc.demo_1203.viewModel.Result;
import com.wj.spc.demo_1203.viewModel.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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


    /**
     * redis 测试
     * @return
     */
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

    /**
     * DiscoveryClient可以注入从注册中心获取的服务相关信息。
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/test04",
            method = RequestMethod.GET)
    public Object test04(){
        List<Province> user = testService.dataSource3();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);
        successResponse.setData(user);

        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("service1");

        return successResponse;
    }

    /**
     * EnumSet。
     */
    @RequestMapping(value = "/enumSet01",
            method = RequestMethod.GET)
    public Object enumSetTest01(){
        testService.enumSetTest01();

        SuccessResponse successResponse = new SuccessResponse<>();
        Result result = new Result();
        result.setCode(0);
        successResponse.setResult(result);

        return successResponse;
    }

    /**
     * word Test
     */
    @RequestMapping(value = "/wordTest01",
            method = RequestMethod.GET)
    public void wordTest01(HttpServletResponse response) throws Exception{
        ServletOutputStream out = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = testService.getWordForJudgement();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.addHeader("Content-Disposition", "attachment;filename=judgement.docx");
            out = response.getOutputStream();
            FileCopyUtils.copy(byteArrayInputStream, out);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
