package com.ahfdkun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.ahfdkun.controller.ConsumerController;

// 成为eureka客户端应用，获得服务发现的能力
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = ConsumerController.class)
public class ConsumerApplication {

	@Bean
	@LoadBalanced // 开启客户端负载均衡
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
