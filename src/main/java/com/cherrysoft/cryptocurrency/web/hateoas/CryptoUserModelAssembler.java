package com.cherrysoft.cryptocurrency.web.hateoas;

import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.web.controller.CryptoCoinController;
import com.cherrysoft.cryptocurrency.web.controller.CryptoUserController;
import com.cherrysoft.cryptocurrency.web.dtos.CryptoUserDTO;
import com.cherrysoft.cryptocurrency.web.mapper.CryptoUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CryptoUserModelAssembler implements RepresentationModelAssembler<CryptoUser, CryptoUserDTO> {
  private final CryptoUserMapper cryptoUserMapper;
  private CryptoUser entity;

  @Override
  @NonNull
  public CryptoUserDTO toModel(@NonNull CryptoUser entity) {
    this.entity = entity;
    CryptoUserDTO userModel = cryptoUserMapper.toCryptoUserDto(entity);
    userModel.add(List.of(selfLink(), ownedCoinsLink(), favoriteCoinsLink()));
    return userModel;
  }

  public Link selfLink() {
    return linkTo(methodOn(CryptoUserController.class)
        .getCryptoUserByUsername(entity.getUsername()))
        .withSelfRel();
  }

  public Link ownedCoinsLink() {
    Map<String, String> params = Map.of("owned", "true");
    return linkTo(methodOn(CryptoCoinController.class)
        .getCryptoCoins(params, null))
        .withRel("ownedCoins");
  }

  public Link favoriteCoinsLink() {
    Map<String, String> params = Map.of("favorite", "true");
    return linkTo(methodOn(CryptoCoinController.class)
        .getCryptoCoins(params, null))
        .withRel("favoriteCoins");
  }

}
