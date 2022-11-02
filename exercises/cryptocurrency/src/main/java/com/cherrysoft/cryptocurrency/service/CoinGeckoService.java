package com.cherrysoft.cryptocurrency.service;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.service.clients.CoinGecko;
import com.cherrysoft.cryptocurrency.service.clients.CoinGeckoClient;
import com.cherrysoft.cryptocurrency.service.clients.CoinGeckoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinGeckoService {
  private final CoinGeckoClient coinGeckoClient;
  private final CoinGeckoMapper coinGeckoMapper;

  public List<CryptoCoin> fetchCryptoCoins() {
    List<CoinGecko> cryptocurrencies = coinGeckoClient.getCryptocurrencies();
    return coinGeckoMapper.toCryptoCoinList(cryptocurrencies);
  }

}
