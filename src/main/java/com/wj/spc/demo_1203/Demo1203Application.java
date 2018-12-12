package com.wj.spc.demo_1203;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Demo1203Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo1203Application.class, args);
	}
}
