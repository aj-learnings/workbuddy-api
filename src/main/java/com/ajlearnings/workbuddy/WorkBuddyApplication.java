package com.ajlearnings.workbuddy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class WorkBuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkBuddyApplication.class, args);
	}

}
