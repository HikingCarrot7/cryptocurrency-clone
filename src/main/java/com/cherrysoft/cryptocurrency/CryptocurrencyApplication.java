package com.cherrysoft.cryptocurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
public class CryptocurrencyApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptocurrencyApplication.class, args);
  }

}
