package com.cherrysoft.cryptocurrency.service.clients;

import com.cherrysoft.cryptocurrency.model.Coin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoinGeckoMapper {

  @Mappings({
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "imageUrl", source = "image"),
      @Mapping(target = "marketCapital", source = "marketCap"),
      @Mapping(target = "marketCapitalRanking", source = "marketCapRank"),
      @Mapping(target = "higherIn24Hours", source = "high_24h"),
      @Mapping(target = "lowerIn24Hours", source = "low_24h")
  })
  Coin toCoin(CoinGecko coinGecko);

  List<Coin> mapToCoinList(List<CoinGecko> coinGecko);

}
