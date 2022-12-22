package com.example.subscriptionmodule.subscriptionapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SubscriptionApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubscriptionApiGatewayApplication.class, args);
	}

}
