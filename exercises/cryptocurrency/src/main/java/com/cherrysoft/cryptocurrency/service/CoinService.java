package com.cherrysoft.cryptocurrency.service;

import com.cherrysoft.cryptocurrency.model.Coin;
import com.cherrysoft.cryptocurrency.repository.CoinRepository;
import com.cherrysoft.cryptocurrency.service.clients.CoinGeckoClient;
import com.cherrysoft.cryptocurrency.service.clients.CoinGeckoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinService {
  private final CoinGeckoClient coinGeckoClient;
  private final CoinGeckoMapper coinGeckoMapper;
  private final CoinRepository coinRepository;

  public List<Coin> getCryptocurrencies() {
    return coinRepository.findAll();
  }

  public Coin saveCoin(Coin coin) {
    return coinRepository.save(coin);
  }

}
