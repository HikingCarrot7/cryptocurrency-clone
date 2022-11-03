package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.controller.dtos.CryptoCoinDTO;
import com.cherrysoft.cryptocurrency.mapper.CryptoCoinMapper;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.security.utils.AuthenticationUtils;
import com.cherrysoft.cryptocurrency.service.CryptoCoinService;
import com.cherrysoft.cryptocurrency.service.criteria.CryptoCoinFilterCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(CryptoCoinController.BASE_URL)
@RequiredArgsConstructor
public class CryptoCoinController {
  public static final String BASE_URL = "/coins";
  private final CryptoCoinService cryptoCoinService;
  private final CryptoCoinMapper cryptoCoinMapper;
  private final AuthenticationUtils authenticationUtils;

  @GetMapping
  public ResponseEntity<List<CryptoCoinDTO>> getCryptoCoins(
      @RequestParam Map<String, String> options
  ) {
    String username = authenticationUtils.getUsername();
    CryptoCoinFilterCriteria filterCriteria = new CryptoCoinFilterCriteria(options);
    List<CryptoCoin> cryptoCoins = cryptoCoinService.getCryptoCoins(username, filterCriteria);
    return ResponseEntity.ok(cryptoCoinMapper.toCryptoCoinDtoList(cryptoCoins));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CryptoCoinDTO> saveCryptoCoin(
      @RequestBody @Valid CryptoCoinDTO cryptoCoinDto
  ) throws URISyntaxException {
    String username = authenticationUtils.getUsername();
    CryptoCoin newCryptoCoin = cryptoCoinService.addCryptoCoinTo(username, cryptoCoinMapper.toCryptoCoin(cryptoCoinDto));
    return ResponseEntity
        .created(new URI(String.format("%s/%s", BASE_URL, newCryptoCoin.getId())))
        .body(cryptoCoinMapper.toCryptoCoinDto(newCryptoCoin));
  }

  @PutMapping("/{id}/toggle-favorite")
  public ResponseEntity<Boolean> toggleFavorite(@PathVariable String id) {
    String username = authenticationUtils.getUsername();
    boolean newState = cryptoCoinService.toggleFavorite(username, id);
    return ResponseEntity.ok(newState);
  }

}
