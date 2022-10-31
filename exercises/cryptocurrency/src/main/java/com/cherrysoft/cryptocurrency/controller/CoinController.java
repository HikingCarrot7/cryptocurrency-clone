package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.controller.dtos.CoinDTO;
import com.cherrysoft.cryptocurrency.mapper.CoinMapper;
import com.cherrysoft.cryptocurrency.model.Coin;
import com.cherrysoft.cryptocurrency.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(CoinController.BASE_URL)
@RequiredArgsConstructor
public class CoinController {
  public static final String BASE_URL = "/coins";
  private final CoinService coinService;
  private final CoinMapper coinMapper;

  @GetMapping
  public ResponseEntity<List<CoinDTO>> getCoins() {
    List<Coin> cryptocurrencies = coinService.getCryptocurrencies();
    return ResponseEntity.ok(coinMapper.mapToCoinDtoList(cryptocurrencies));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CoinDTO> saveCoin(@RequestBody @Valid CoinDTO coinDto) throws URISyntaxException {
    Coin newCoin = coinService.saveCoin(coinMapper.toCoin(coinDto));
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, newCoin.getId())))
        .body(coinMapper.toCoinDto(newCoin));
  }

}
