package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.controller.dtos.CryptoUserDTO;
import com.cherrysoft.cryptocurrency.controller.dtos.LoginResponseDTO;
import com.cherrysoft.cryptocurrency.mapper.CryptoUserMapper;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.security.service.LoginService;
import com.cherrysoft.cryptocurrency.service.CryptoUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class LoginAndRegistrationController {
  private final CryptoUserMapper cryptoUserMapper;
  private final LoginService loginService;
  private final CryptoUserService cryptoUserService;

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid CryptoUserDTO cryptoUserDto) {
    CryptoUser cryptoUser = cryptoUserMapper.toCryptoUser(cryptoUserDto);
    return ResponseEntity.ok(loginService.login(cryptoUser));
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CryptoUserDTO> register(@RequestBody @Valid CryptoUserDTO cryptoUserDto) {
    CryptoUser cryptoUser = cryptoUserMapper.toCryptoUser(cryptoUserDto);
    CryptoUser newCryptoUser = cryptoUserService.createCryptoUser(cryptoUser);
    return ResponseEntity.ok(cryptoUserMapper.toCryptoUserDto(newCryptoUser));
  }

}
