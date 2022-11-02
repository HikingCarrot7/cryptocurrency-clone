package com.cherrysoft.cryptocurrency.service;

import com.cherrysoft.cryptocurrency.exception.CryptoCoinNotFoundException;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.repository.CryptoCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CryptoCoinService {
  private final CryptoCoinRepository cryptoCoinRepository;

  public List<CryptoCoin> getCryptoCoins() {
    return cryptoCoinRepository.findAll();
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

  public CryptoCoin toggleFavorite(String id) {
    CryptoCoin cryptoCoin = getCryptoCoin(id);
    cryptoCoin.setIsFavorite(!cryptoCoin.isFavorite());
    return cryptoCoinRepository.save(cryptoCoin);
  }

}
