package com.homestaywithme.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HomestaywithmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomestaywithmeApplication.class, args);
	}

}
