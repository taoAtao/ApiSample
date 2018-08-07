package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableZuulProxy//启用zuul的代理服务器功能
//@EnableDiscoveryClient//启动服务注册发现功能
//@EnableJpaRepositories("com.gateway.repositories")
//@EntityScan({"com.gateway.models"})
//@ComponentScan({"com.gateway.filters","com.gateway.services"})
@SpringBootApplication

public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
