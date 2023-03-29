package com.bankx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BankxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankxApplication.class, args);
	}
}
