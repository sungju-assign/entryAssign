package com.sungjujjang.entryAssgin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EntryAssginApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntryAssginApplication.class, args);
	}

}
