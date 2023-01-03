package com.cherrysoft.cryptocurrency.web.mapper;

import com.cherrysoft.cryptocurrency.web.dtos.CryptoUserDTO;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CryptoUserMapper {

  CryptoUser toCryptoUser(CryptoUserDTO cryptoUserDto);

  CryptoUserDTO toCryptoUserDto(CryptoUser cryptoUser);

}
