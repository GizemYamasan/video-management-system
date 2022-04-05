package com.emlakjet.videostore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class VideoManagementSystemApplication {

	public static void main(String[] args) {
		log.info("starting video management system app...");
		SpringApplication.run(VideoManagementSystemApplication.class, args);
	}

}
