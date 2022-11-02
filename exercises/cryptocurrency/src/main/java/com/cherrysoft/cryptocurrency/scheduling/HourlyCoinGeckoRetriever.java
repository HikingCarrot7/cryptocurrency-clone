package com.cherrysoft.cryptocurrency.scheduling;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.service.CoinGeckoService;
import com.cherrysoft.cryptocurrency.service.CryptoCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class HourlyCoinGeckoRetriever {
  private final CoinGeckoService coinGeckoService;
  private final CryptoCoinService cryptoCoinService;

  @Scheduled(fixedDelay = 60 * 60, initialDelay = 60 * 60, timeUnit = TimeUnit.SECONDS)
  public void scheduleCoinGeckoServiceRetrieval() {
    List<CryptoCoin> fetchedCryptos = coinGeckoService.fetchCryptoCoins();
    for (CryptoCoin fetchedCrypto : fetchedCryptos) {
      cryptoCoinService.getCryptoCoinOptional(fetchedCrypto.getId())
          .ifPresentOrElse(
              (savedCrypto) -> {
                copyCryptoProperties(fetchedCrypto, savedCrypto);
                cryptoCoinService.saveCryptoCoin(savedCrypto);
              },
              () -> {
                cryptoCoinService.saveCryptoCoin(fetchedCrypto);
              }
          );
    }
  }

  private void copyCryptoProperties(CryptoCoin source, CryptoCoin target) {
    target.setName(source.getName());
    target.setSymbol(source.getSymbol());
    target.setCurrentPrice(source.getCurrentPrice());
    target.setMarketCapital(source.getMarketCapital());
    target.setTotalVolume(source.getTotalVolume());
    target.setMarketCapitalRanking(source.getMarketCapitalRanking());
    target.setHigherIn24Hours(source.getHigherIn24Hours());
    target.setLowerIn24Hours(source.getLowerIn24Hours());
  }

}
