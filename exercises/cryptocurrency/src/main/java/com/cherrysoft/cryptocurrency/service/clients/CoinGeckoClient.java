package com.cherrysoft.cryptocurrency.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "gecko", url = "https://api.coingecko.com/api/v3/")
public interface CoinGeckoClient {

  @RequestMapping(
      method = RequestMethod.GET,
      value = "/coins/markets?vs_currency=usd"
  )
  List<CoinGecko> getCryptocurrencies();

}
