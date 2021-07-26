package com.hjbm.mscentroservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableHystrix
public class MsCentroserviciosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCentroserviciosApplication.class, args);
	}

}
