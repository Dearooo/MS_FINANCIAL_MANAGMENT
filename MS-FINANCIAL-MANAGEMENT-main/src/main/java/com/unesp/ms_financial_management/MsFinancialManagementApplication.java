package com.unesp.ms_financial_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MsFinancialManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsFinancialManagementApplication.class, args);
	}

}
