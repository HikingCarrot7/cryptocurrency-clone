package com.cherrysoft.cryptocurrency.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.isNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crypto_coins")
public class CryptoCoin {
  @Id
  @Column(name = "coin_id")
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

  @Column
  @Getter(AccessLevel.NONE)
  private boolean isPublic = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
  @ToString.Exclude
  private CryptoUser owner;

  @ManyToMany(mappedBy = "favoriteCoins")
  @ToString.Exclude
  private Set<CryptoUser> markedFavoriteBy = new LinkedHashSet<>();

  @PrePersist
  @PreUpdate
  void setIdIfMissing() {
    if (isNull(getId())) {
      setId(UUID.randomUUID().toString());
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    CryptoCoin that = (CryptoCoin) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public boolean getIsPublic() {
    return isPublic;
  }

}
