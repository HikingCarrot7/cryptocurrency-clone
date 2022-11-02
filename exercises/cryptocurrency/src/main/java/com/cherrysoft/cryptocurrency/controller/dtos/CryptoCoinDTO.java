package com.cherrysoft.cryptocurrency.controller.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CryptoCoinDTO {
  private final String id;

  @NotNull
  private final String name;

  @NotNull
  private final String symbol;

  @NotNull
  private final String imageUrl;

  @NotNull
  private final BigDecimal currentPrice;

  @NotNull
  private final BigDecimal marketCapital;

  @NotNull
  private final BigDecimal totalVolume;

  @NotNull
  private final Integer marketCapitalRanking;

  @NotNull
  private final BigDecimal higherIn24Hours;

  @NotNull
  private final BigDecimal lowerIn24Hours;

}
