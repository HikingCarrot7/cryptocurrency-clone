package com.cherrysoft.cryptocurrency.service.clients;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoinGeckoMapper {

  @Mappings({
      @Mapping(target = "imageUrl", source = "image"),
      @Mapping(target = "marketCapital", source = "marketCap"),
      @Mapping(target = "marketCapitalRanking", source = "marketCapRank"),
      @Mapping(target = "higherIn24Hours", source = "high_24h"),
      @Mapping(target = "lowerIn24Hours", source = "low_24h")
  })
  CryptoCoin toCryptoCoin(CoinGecko coinGecko);

  List<CryptoCoin> toCryptoCoinList(List<CoinGecko> coinGecko);

}
