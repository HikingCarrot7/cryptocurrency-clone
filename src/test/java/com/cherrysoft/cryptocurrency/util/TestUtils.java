package com.cherrysoft.cryptocurrency.util;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TestUtils {
  private final Faker faker;

  public CryptoCoin createCryptoCoin() {
    return CryptoCoin.builder()
        .name(faker.funnyName().name())
        .symbol(faker.animal().name())
        .imageUrl(faker.internet().url())
        .currentPrice(randomBigDecimal())
        .marketCapital(randomBigDecimal())
        .totalVolume(randomBigDecimal())
        .marketCapitalRanking(faker.number().numberBetween(1, 30))
        .higherIn24Hours(randomBigDecimal())
        .lowerIn24Hours(randomBigDecimal())
        .isPublic(false)
        .build();
  }

  public CryptoUser createCryptoUser() {
    return CryptoUser.builder()
        .username(faker.name().username())
        .password("password")
        .build();
  }

  private BigDecimal randomBigDecimal() {
    return BigDecimal.valueOf(Double.parseDouble(faker.number().digits(8)));
  }

}
