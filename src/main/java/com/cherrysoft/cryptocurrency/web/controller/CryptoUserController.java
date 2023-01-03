package com.cherrysoft.cryptocurrency.web.controller;

import com.cherrysoft.cryptocurrency.model.CryptoUser;
import com.cherrysoft.cryptocurrency.service.CryptoUserService;
import com.cherrysoft.cryptocurrency.web.dtos.CryptoUserDTO;
import com.cherrysoft.cryptocurrency.web.hateoas.CryptoUserModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.cherrysoft.cryptocurrency.util.ApiDocsConstants.*;

@RestController
@RequestMapping(CryptoUserController.BASE_URL)
@RequiredArgsConstructor
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(ref = INTERNAL_SERVER_ERROR_RESPONSE_REF, responseCode = "500")
})
public class CryptoUserController {
  public static final String BASE_URL = "/users";
  private final CryptoUserService cryptoUserService;
  private final CryptoUserModelAssembler cryptoUserModelAssembler;

  @Operation(summary = "Get crypto user with the provided ID")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = CryptoUserDTO.class))
  })
  @GetMapping
  public CryptoUserDTO getCryptoUserByUsername(@RequestParam String username) {
    CryptoUser result = cryptoUserService.getCryptoUserByUsername(username);
    return cryptoUserModelAssembler.toModel(result);
  }

}
