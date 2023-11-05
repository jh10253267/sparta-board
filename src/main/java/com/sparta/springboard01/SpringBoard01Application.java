package com.sparta.springboard01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBoard01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoard01Application.class, args);
	}

}
