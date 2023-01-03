package com.cherrysoft.cryptocurrency.controller;

import com.cherrysoft.cryptocurrency.controller.dtos.CryptoCoinDTO;
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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static com.cherrysoft.cryptocurrency.util.ApiDocsConstants.*;

@RestController
@RequestMapping(CryptoCoinController.BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Crypto Coin Controller", description = "Manage crypto coins of users")
@ApiResponses({
    @ApiResponse(ref = BAD_REQUEST_RESPONSE_REF, responseCode = "400"),
    @ApiResponse(ref = UNAUTHORIZED_RESPONSE_REF, responseCode = "401"),
    @ApiResponse(ref = FORBIDDEN_RESPONSE_REF, responseCode = "403"),
    @ApiResponse(ref = NOT_FOUND_RESPONSE_REF, responseCode = "404"),
    @ApiResponse(ref = INTERNAL_SERVER_ERROR_RESPONSE_REF, responseCode = "500")
})
public class CryptoCoinController {
  public static final String BASE_URL = "/coins";
  private final CryptoCoinService cryptoCoinService;
  private final CryptoCoinMapper cryptoCoinMapper;
  private final AuthenticationUtils authenticationUtils;

  @Operation(summary = "Get crypto coins", description = "Description")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CryptoCoinDTO.class)))
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
      )
  })
  @GetMapping
  public ResponseEntity<List<CryptoCoinDTO>> getCryptoCoins(
      @RequestParam @Parameter(hidden = true) Map<String, String> options,
      @ParameterObject
      @PageableDefault
      @SortDefault(sort = "currentPrice", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    String username = authenticationUtils.getUsername();
    CryptoCoinFilterCriteria filterCriteria = new CryptoCoinFilterCriteria(options, pageable);
    List<CryptoCoin> cryptoCoins = cryptoCoinService.getCryptoCoins(username, filterCriteria);
    return ResponseEntity.ok(cryptoCoinMapper.toCryptoCoinDtoList(cryptoCoins));
  }

  @Operation(summary = "Create crypto coin for logged user", description = "Description")
  @ApiResponse(responseCode = "201", description = "Created", content = {
      @Content(schema = @Schema(implementation = CryptoCoinDTO.class))
  })
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

  @Operation(summary = "Toggle crypto coin with {id} as favorite", description = "Description")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(type = "boolean"))
  })
  @PutMapping("/{id}/toggle-favorite")
  public ResponseEntity<Boolean> toggleFavorite(@PathVariable String id) {
    String username = authenticationUtils.getUsername();
    boolean newState = cryptoCoinService.toggleFavorite(username, id);
    return ResponseEntity.ok(newState);
  }

}
