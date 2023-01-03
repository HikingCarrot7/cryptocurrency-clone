package com.cherrysoft.cryptocurrency.service;

import com.cherrysoft.cryptocurrency.exception.CryptoCoinNotFoundException;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.model.FavoriteResult;
import com.cherrysoft.cryptocurrency.repository.CryptoCoinRepository;
import com.cherrysoft.cryptocurrency.repository.CryptoUserRepository;
import com.cherrysoft.cryptocurrency.service.criteria.CryptoCoinFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoCoinService {
  private final CryptoUserService cryptoUserService;
  private final CryptoCoinRepository cryptoCoinRepository;
  private final CryptoUserRepository cryptoUserRepository;

  public Page<CryptoCoin> getCryptoCoins(String username, CryptoCoinFilterCriteria criteria) {
    Pageable pageable = criteria.getPageable();
    if (criteria.filterByFavoriteCoins()) {
      return cryptoCoinRepository.findAllCryptoCoinsMarkedFavoriteBy(username, pageable);
    }
    if (criteria.filterByOwnedCoins()) {
      return cryptoCoinRepository.findAllCryptoCoinsOwnedBy(username, pageable);
    }
    if (criteria.filterByPublicCoins()) {
      return cryptoCoinRepository.findAllByIsPublicTrue(pageable);
    }
    return cryptoCoinRepository.findAllPublicAndOwnedByCryptoCoins(username, pageable);
  }

  public Optional<CryptoCoin> getCryptoCoinOptional(String id) {
    return cryptoCoinRepository.findById(id);
  }

  public CryptoCoin getCryptoCoin(String id) {
    return getCryptoCoinOptional(id)
        .orElseThrow(() -> new CryptoCoinNotFoundException(id));
  }

  public CryptoCoin saveCryptoCoin(CryptoCoin cryptoCoin) {
    return cryptoCoinRepository.save(cryptoCoin);
  }

  public CryptoCoin addCryptoCoinTo(String username, CryptoCoin cryptoCoin) {
    CryptoUser cryptoUser = cryptoUserService.getCryptoUserByUsername(username);
    cryptoCoin.setPublic(false);
    cryptoUser.addCryptoCoin(cryptoCoin);
    return saveCryptoCoin(cryptoCoin);
  }

  public FavoriteResult markAsFavorite(String username, String cryptoCoinId) {
    CryptoUser cryptoUser = cryptoUserService.getCryptoUserByUsername(username);
    CryptoCoin cryptoCoin = getCryptoCoin(cryptoCoinId);
    boolean wasMarkedAsFavorite = cryptoUser.isFavorite(cryptoCoin);
    if (wasMarkedAsFavorite) {
      cryptoUser.removeFavorite(cryptoCoin);
    } else {
      cryptoUser.addFavorite(cryptoCoin);
    }
    cryptoUserRepository.saveAndFlush(cryptoUser);
    return new FavoriteResult(wasMarkedAsFavorite, !wasMarkedAsFavorite);
  }

}
