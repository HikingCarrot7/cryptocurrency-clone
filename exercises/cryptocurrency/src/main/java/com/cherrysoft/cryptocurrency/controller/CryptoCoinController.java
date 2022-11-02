package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.controller.dtos.CryptoCoinDTO;
import com.cherrysoft.cryptocurrency.mapper.CryptoCoinMapper;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.service.CryptoCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AbstractSingletonProxyFactoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(CryptoCoinController.BASE_URL)
@RequiredArgsConstructor
public class CryptoCoinController {
  public static final String BASE_URL = "/coins";
  private final CryptoCoinService cryptoCoinService;
  private final CryptoCoinMapper cryptoCoinMapper;

  @GetMapping
  public ResponseEntity<List<CryptoCoinDTO>> getCryptoCoins() {
    List<CryptoCoin> cryptoCoins = cryptoCoinService.getCryptoCoins();
    System.out.println(AbstractSingletonProxyFactoryBean.class);
    return ResponseEntity.ok(cryptoCoinMapper.toCryptoCoinDtoList(cryptoCoins));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CryptoCoinDTO> saveCryptoCoin(
      @RequestBody @Valid CryptoCoinDTO cryptoCoinDto
  ) throws URISyntaxException {
    CryptoCoin newCryptoCoin = cryptoCoinService.saveCryptoCoin(cryptoCoinMapper.toCryptoCoin(cryptoCoinDto));
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, newCoin.getId())))
        .body(coinMapper.toCoinDto(newCoin));
  }

}
