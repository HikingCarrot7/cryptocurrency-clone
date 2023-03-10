package com.cherrysoft.cryptocurrency.repository;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoCoinRepository extends JpaRepository<CryptoCoin, String> {

  @Query("SELECT c FROM CryptoCoin c JOIN c.markedFavoriteBy user WHERE user.username = :username")
  Page<CryptoCoin> findAllCryptoCoinsMarkedFavoriteBy(String username, Pageable page);

  @Query("SELECT c FROM CryptoCoin c JOIN c.owner owner WHERE owner.username = :username")
  Page<CryptoCoin> findAllCryptoCoinsOwnedBy(String username, Pageable page);

  @Query("SELECT c FROM CryptoCoin c " +
      "WHERE c.id IN (SELECT c1.id FROM CryptoCoin c1 WHERE c1.isPublic = TRUE) " +
      "OR c.id IN (SELECT c2.id FROM CryptoCoin c2 WHERE c2.owner.username = :username)")
  Page<CryptoCoin> findAllPublicAndOwnedByCryptoCoins(String username, Pageable page);

  Page<CryptoCoin> findAllByIsPublicTrue(Pageable page);

}
