package com.cherrysoft.cryptocurrency.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class CryptoUser {
  @Id
  @Column(name = "user_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(
      mappedBy = "owner",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @ToString.Exclude
  private Set<CryptoCoin> ownedCoins = new HashSet<>();

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "users_favorite_coins",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "coin_id")
  )
  @ToString.Exclude
  private Set<CryptoCoin> favoriteCoins = new LinkedHashSet<>();

  public void addCryptoCoin(CryptoCoin cryptoCoin) {
    ownedCoins.add(cryptoCoin);
    cryptoCoin.setOwner(this);
  }

  public void addFavorite(CryptoCoin cryptoCoin) {
    favoriteCoins.add(cryptoCoin);
    cryptoCoin.getMarkedFavoriteBy().add(this);
  }

  public void removeFavorite(CryptoCoin cryptoCoin) {
    favoriteCoins.remove(cryptoCoin);
    cryptoCoin.getMarkedFavoriteBy().remove(this);
  }

  public boolean isFavorite(CryptoCoin cryptoCoin) {
    return favoriteCoins.contains(cryptoCoin);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    CryptoUser cryptoUser = (CryptoUser) o;
    return id != null && Objects.equals(id, cryptoUser.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
