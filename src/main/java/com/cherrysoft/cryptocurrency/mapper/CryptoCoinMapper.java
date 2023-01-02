package com.cherrysoft.cryptocurrency.mapper;

import com.cherrysoft.cryptocurrency.controller.dtos.CryptoCoinDTO;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CryptoCoinMapper {

  CryptoCoinDTO toCryptoCoinDto(CryptoCoin cryptoCoin);

  CryptoCoin toCryptoCoin(CryptoCoinDTO cryptoCoinDto);

  List<CryptoCoinDTO> toCryptoCoinDtoList(List<CryptoCoin> cryptoCoins);

}
