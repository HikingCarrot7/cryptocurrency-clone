package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.controller.dtos.CryptoCoinDTO;
import com.cherrysoft.cryptocurrency.exception.ErrorResponse;
import com.cherrysoft.cryptocurrency.mapper.CryptoCoinMapper;
import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.security.utils.AuthenticationUtils;
import com.cherrysoft.cryptocurrency.service.CryptoCoinService;
import com.cherrysoft.cryptocurrency.service.criteria.CryptoCoinFilterCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Crypto Coin Controller", description = "Manage crypto coins of users")
public class CryptoCoinController {
  public static final String BASE_URL = "/coins";
  private final CryptoCoinService cryptoCoinService;
  private final CryptoCoinMapper cryptoCoinMapper;
  private final AuthenticationUtils authenticationUtils;

  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CryptoCoinDTO.class)))
  })
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @Parameters({
      @Parameter(in = ParameterIn.QUERY, name = "favorite", schema = @Schema(type = "string"),
          description = "Return user's favorite coins"
      ),
      @Parameter(in = ParameterIn.QUERY, name = "public", schema = @Schema(type = "string"),
          description = "Return all public coins"
      ),
      @Parameter(in = ParameterIn.QUERY, name = "owned", schema = @Schema(type = "string"),
          description = "Return owned coins"
      ),
      @Parameter(in = ParameterIn.QUERY, name = "all", schema = @Schema(type = "string"),
          description = "Return public and owned coins"
      ),
      @Parameter(in = ParameterIn.QUERY, name = "page", schema = @Schema(type = "string", defaultValue = "0"),
          description = "Zero-based page index (0..N)"
      ),
      @Parameter(in = ParameterIn.QUERY, name = "size", schema = @Schema(type = "string", defaultValue = "10"),
          description = "The size of the page"
      )
  })
  @Operation(summary = "Get crypto coins", description = "Description")
  @GetMapping
  public ResponseEntity<List<CryptoCoinDTO>> getCryptoCoins(
      @RequestParam @Parameter(hidden = true) Map<String, String> options
  ) {
    String username = authenticationUtils.getUsername();
    CryptoCoinFilterCriteria filterCriteria = new CryptoCoinFilterCriteria(options);
    List<CryptoCoin> cryptoCoins = cryptoCoinService.getCryptoCoins(username, filterCriteria);
    return ResponseEntity.ok(cryptoCoinMapper.toCryptoCoinDtoList(cryptoCoins));
  }

  @ApiResponse(responseCode = "201", description = "Created", content = {
      @Content(schema = @Schema(implementation = CryptoCoinDTO.class))
  })
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @Operation(summary = "Create crypto coin for logged user", description = "Description")
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

  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(type = "boolean"))
  })
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
      @Content(schema = @Schema(implementation = ErrorResponse.class))
  })
  @Parameter(in = ParameterIn.PATH, name = "id", schema = @Schema(type = "string"),
      description = "A crypto coin id"
  )
  @Operation(summary = "Toggle crypto coin with {id} as favorite", description = "Description")
  @PutMapping("/{id}/toggle-favorite")
  public ResponseEntity<Boolean> toggleFavorite(@PathVariable String id) {
    String username = authenticationUtils.getUsername();
    boolean newState = cryptoCoinService.toggleFavorite(username, id);
    return ResponseEntity.ok(newState);
  }

}
