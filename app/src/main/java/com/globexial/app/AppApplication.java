package com.globexial.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(
		basePackages = "com.globexial.repository"
)
@EntityScan(
		basePackages = "com.globexial.model.entity"
)
@SpringBootApplication(
		scanBasePackages = "com.globexial"
)
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
