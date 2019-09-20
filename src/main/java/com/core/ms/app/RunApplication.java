package com.core.ms.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = { "com.core.ms.controller" })
@EnableMongoRepositories("com.core.ms.interfaz")
@SpringBootApplication
public class RunApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}

}
