package com.cherrysoft.cryptocurrency.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class FakerConfig {

  @Bean
  public Faker fakerInstance() {
    return new Faker(new Locale("es-MX"));
  }

}
