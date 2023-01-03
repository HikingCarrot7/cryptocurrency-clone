package com.cherrysoft.cryptocurrency.web.controller;

import com.cherrysoft.cryptocurrency.web.controller.dtos.CryptoUserDTO;
import com.cherrysoft.cryptocurrency.web.controller.dtos.LoginResponseDTO;
import com.cherrysoft.cryptocurrency.mapper.CryptoUserMapper;
import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.security.SecurityCryptoUser;
import com.cherrysoft.cryptocurrency.security.TokenGenerator;
import com.cherrysoft.cryptocurrency.service.CryptoUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.cherrysoft.cryptocurrency.util.ApiDocsConstants.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login And Registration Controller", description = "Login and registration of users")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(ref = INTERNAL_SERVER_ERROR_RESPONSE_REF, responseCode = "500")
})
public class LoginAndRegistrationController {
  private final CryptoUserService cryptoUserService;
  private final CryptoUserMapper cryptoUserMapper;
  private final TokenGenerator tokenGenerator;
  private final DaoAuthenticationProvider daoAuthenticationProvider;

  @Operation(summary = "Login for registered user", description = "Description")
  @ApiResponse(responseCode = "200", description = "User Logged In", content = {
      @Content(schema = @Schema(implementation = LoginResponseDTO.class))
  })
  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid CryptoUserDTO cryptoUserDto) {
    var token = UsernamePasswordAuthenticationToken.unauthenticated(cryptoUserDto.getUsername(), cryptoUserDto.getPassword());
    Authentication authentication = daoAuthenticationProvider.authenticate(token);
    return ResponseEntity.ok(tokenGenerator.issueToken(authentication));
  }

  @Operation(summary = "Registration of new user", description = "Description")
  @ApiResponse(responseCode = "201", description = "User Registered", content = {
      @Content(schema = @Schema(implementation = CryptoUserDTO.class))
  })
  @PostMapping("/register")
  public ResponseEntity<LoginResponseDTO> register(@RequestBody @Valid CryptoUserDTO cryptoUserDto) {
    CryptoUser cryptoUser = cryptoUserMapper.toCryptoUser(cryptoUserDto);
    CryptoUser result = cryptoUserService.createCryptoUser(cryptoUser);
    SecurityCryptoUser securityUser = new SecurityCryptoUser(result);
    Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(securityUser, securityUser.getPassword(), securityUser.getAuthorities());
    return ResponseEntity.ok(tokenGenerator.issueToken(authentication));
  }

}
