package com.assignment.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.assignment")
public class AtmDispenserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtmDispenserApplication.class, args);
	}
	
}
