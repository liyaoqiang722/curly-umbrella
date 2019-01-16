package com.wj.spc.demo_1203.servuice.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wj.spc.demo_1203.config.kafka.Message;
import com.wj.spc.demo_1203.servuice.SendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by liyaoqiang on 19/1/16.
 */
@Service
@Slf4j
public class SendServiceImpl implements SendService {

    /**
     *
     */
    @Resource
    private KafkaTemplate kafkaTemplate;

    @Override
    public void send() {
        Gson gson = new GsonBuilder().create();
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(UUID.randomUUID().toString());
        message.setSendTime(new Date());
        log.info("+++++++++++++++++++++  message = {}", gson.toJson(message));
        kafkaTemplate.send("test01", gson.toJson(message));
    }
}
