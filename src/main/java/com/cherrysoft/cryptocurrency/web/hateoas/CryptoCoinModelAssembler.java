package com.cherrysoft.cryptocurrency.web.hateoas;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.web.controller.CryptoUserController;
import com.cherrysoft.cryptocurrency.web.dtos.CryptoCoinDTO;
import com.cherrysoft.cryptocurrency.web.mapper.CryptoCoinMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CryptoCoinModelAssembler implements RepresentationModelAssembler<CryptoCoin, CryptoCoinDTO> {
  private final CryptoCoinMapper cryptoCoinMapper;
  private CryptoCoin entity;

  @Override
  @NonNull
  public CryptoCoinDTO toModel(@NonNull CryptoCoin entity) {
    this.entity = entity;
    CryptoCoinDTO cryptoCoinModel = cryptoCoinMapper.toCryptoCoinDto(entity);
    if (entity.hasOwner()) {
      cryptoCoinModel.add(List.of(ownerLink()));
    }
    return cryptoCoinModel;
  }

  public Link ownerLink() {
    CryptoUser owner = entity.getOwner();
    return linkTo(methodOn(CryptoUserController.class)
        .getCryptoUserByUsername(owner.getUsername()))
        .withRel("owner");
  }

}
