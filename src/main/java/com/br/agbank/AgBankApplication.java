package com.br.agbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgBankApplication.class, args);
	}

}
