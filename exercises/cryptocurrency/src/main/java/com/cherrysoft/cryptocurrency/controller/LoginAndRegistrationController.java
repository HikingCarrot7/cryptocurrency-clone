package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.controller.dtos.CryptoUserDTO;
import com.cherrysoft.cryptocurrency.controller.dtos.LoginResponseDTO;
import com.cherrysoft.cryptocurrency.exception.ErrorResponse;
import com.cherrysoft.cryptocurrency.mapper.CryptoUserMapper;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.security.service.LoginService;
import com.cherrysoft.cryptocurrency.service.CryptoUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login And Registration Controller", description = "Login and registration of users")
public class LoginAndRegistrationController {
  private final CryptoUserMapper cryptoUserMapper;
  private final LoginService loginService;
  private final CryptoUserService cryptoUserService;

  @ApiResponse(responseCode = "200", description = "User Logged In", content = {
      @Content(schema = @Schema(implementation = LoginResponseDTO.class))
  })
  @ApiResponse(responseCode = "400", description = "Bad Credentials", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @Operation(summary = "Login for registered user", description = "Description")
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid CryptoUserDTO cryptoUserDto) {
    CryptoUser cryptoUser = cryptoUserMapper.toCryptoUser(cryptoUserDto);
    return ResponseEntity.ok(loginService.login(cryptoUser));
  }

  @ApiResponse(responseCode = "201", description = "User Registered", content = {
      @Content(schema = @Schema(implementation = CryptoUserDTO.class))
  })
  @ApiResponse(responseCode = "400", description = "Bad User Input", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @Operation(summary = "Registration of new user", description = "Description")
  @PostMapping("/register")
  public ResponseEntity<CryptoUserDTO> register(@RequestBody @Valid CryptoUserDTO cryptoUserDto) {
    CryptoUser cryptoUser = cryptoUserMapper.toCryptoUser(cryptoUserDto);
    CryptoUser newCryptoUser = cryptoUserService.createCryptoUser(cryptoUser);
    return ResponseEntity.ok(cryptoUserMapper.toCryptoUserDto(newCryptoUser));
  }

}
