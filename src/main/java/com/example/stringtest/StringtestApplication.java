package com.example.stringtest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

import static com.example.stringtest.LoanController.testCanConnect;

@SpringBootApplication
public class StringtestApplication {

	public static void main(String[] args) {

		SpringApplication.run(StringtestApplication.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			testCanConnect();

			System.out.println("Let's inspect the beans provided by Spring Boot dude:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	};

}
