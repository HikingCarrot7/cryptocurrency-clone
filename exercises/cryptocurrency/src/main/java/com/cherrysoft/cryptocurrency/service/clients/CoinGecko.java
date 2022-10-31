package com.cherrysoft.cryptocurrency.service.clients;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoinGecko {
  private String id;
  private String name;
  private String symbol;
  private String image;
  private BigDecimal currentPrice;
  private BigDecimal marketCap;
  private BigDecimal totalVolume;
  private Integer marketCapRank;
  private BigDecimal high_24h;
  private BigDecimal low_24h;
}
