package com.cherrysoft.cryptocurrency.web.mapper;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.web.dtos.CryptoCoinDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CryptoCoinMapper {

  CryptoCoinDTO toCryptoCoinDto(CryptoCoin cryptoCoin);

  CryptoCoin toCryptoCoin(CryptoCoinDTO cryptoCoinDto);

}
