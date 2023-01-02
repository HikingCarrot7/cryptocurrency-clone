package com.cherrysoft.cryptocurrency.service;

import com.cherrysoft.cryptocurrency.exception.CryptoCoinNotFoundException;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.repository.CryptoCoinRepository;
import com.cherrysoft.cryptocurrency.service.criteria.CryptoCoinFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoCoinService {
  private final CryptoUserService cryptoUserService;
  private final CryptoCoinRepository cryptoCoinRepository;

  public List<CryptoCoin> getCryptoCoins(String username, CryptoCoinFilterCriteria criteria) {
    Pageable page = PageRequest.of(criteria.page(), criteria.pageSize(), Sort.by("currentPrice").descending());
    if (criteria.filterByFavoriteCoins()) {
      return cryptoCoinRepository.findAllCryptoCoinsMarkedFavoriteBy(username, page);
    }
    if (criteria.filterByOwnedCoins()) {
      return cryptoCoinRepository.findAllCryptoCoinsOwnedBy(username, page);
    }
    if (criteria.filterByPublicCoins()) {
      return cryptoCoinRepository.findAllByIsPublicTrue(page);
    }
    return cryptoCoinRepository.findAllPublicAndOwnedByCryptoCoins(username, page);
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

  @Transactional
  public CryptoCoin addCryptoCoinTo(String username, CryptoCoin cryptoCoin) {
    CryptoUser cryptoUser = cryptoUserService.getCryptoUserByUsername(username);
    cryptoCoin.setPublic(false);
    cryptoUser.addCryptoCoin(cryptoCoin);
    return saveCryptoCoin(cryptoCoin);
  }

  @Transactional
  public boolean toggleFavorite(String username, String cryptoCoinId) {
    CryptoUser cryptoUser = cryptoUserService.getCryptoUserByUsername(username);
    CryptoCoin cryptoCoin = getCryptoCoin(cryptoCoinId);
    if (cryptoUser.isFavorite(cryptoCoin)) {
      cryptoUser.removeFavorite(cryptoCoin);
      return false;
    } else {
      cryptoUser.addFavorite(cryptoCoin);
      return true;
    }
  }

}
