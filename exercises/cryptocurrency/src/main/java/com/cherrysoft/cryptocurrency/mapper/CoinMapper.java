package com.cherrysoft.cryptocurrency.mapper;

import com.cherrysoft.cryptocurrency.controller.dtos.CoinDTO;
import com.cherrysoft.cryptocurrency.model.Coin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CoinMapper {

  CoinDTO toCoinDto(Coin coin);

  Coin toCoin(CoinDTO coinDto);

  List<CoinDTO> mapToCoinDtoList(List<Coin> coins);

}
