package com.cherrysoft.cryptocurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CryptocurrencyApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptocurrencyApplication.class, args);
  }

}
