package com.plateform.multiplayer_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultiplayerPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiplayerPlatformApplication.class, args);
	}

}