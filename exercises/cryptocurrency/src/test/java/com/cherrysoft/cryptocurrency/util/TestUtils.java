package com.cherrysoft.cryptocurrency.util;

import com.cherrysoft.cryptocurrency.config.FakerConfig;
import com.cherrysoft.cryptocurrency.model.Coin;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class TestUtils {
  public static Faker faker = FakerConfig.FAKER_INSTANCE;

  public static class CryptoCoin {

    public static Coin createCryptoCoin() {
      return Coin.builder()
          .name(faker.funnyName().name())
          .symbol(faker.animal().name())
          .imageUrl(faker.internet().url())
          .currentPrice(randomBigDecimal())
          .marketCapital(randomBigDecimal())
          .totalVolume(randomBigDecimal())
          .marketCapitalRanking(faker.number().numberBetween(1, 30))
          .higherIn24Hours(randomBigDecimal())
          .lowerIn24Hours(randomBigDecimal())
          .build();
    }

    private static BigDecimal randomBigDecimal() {
      return BigDecimal.valueOf(Double.parseDouble(faker.number().digits(8)));
    }

  }

}
