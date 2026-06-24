package com.swoovo.friends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class FriendsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsServiceApplication.class, args);
	}

}
