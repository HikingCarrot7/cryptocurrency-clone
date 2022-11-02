package com.cherrysoft.cryptocurrency.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.isNull;

@Entity
@Table(name = "crypto_coins")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoCoin {
  @Id
  private String id;

  @Column
  private String name;

  @Column
  private String symbol;

  @Column
  private String imageUrl;

  @Column
  private BigDecimal currentPrice;

  @Column
  private BigDecimal marketCapital;

  @Column
  private BigDecimal totalVolume;

  @Column
  private Integer marketCapitalRanking;

  @Column
  private BigDecimal higherIn24Hours;

  @Column
  private BigDecimal lowerIn24Hours;

}
