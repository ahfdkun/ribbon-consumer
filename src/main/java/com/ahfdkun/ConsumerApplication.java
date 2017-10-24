package com.ahfdkun;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*// 成为eureka客户端应用，获得服务发现的能力
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackageClasses = ConsumerController.class)
// 开启断路器功能
@EnableCircuitBreaker*/

// 替代上面的注解
@SpringCloudApplication
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
