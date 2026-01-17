package com.bankingapp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BankingAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankingAppApplication.class, args);
  }
}
