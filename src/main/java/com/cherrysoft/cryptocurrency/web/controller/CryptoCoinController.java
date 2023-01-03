package com.cherrysoft.cryptocurrency.web.controller;

import com.cherrysoft.cryptocurrency.model.CryptoCoin;
import com.cherrysoft.cryptocurrency.model.FavoriteResult;
import com.cherrysoft.cryptocurrency.security.utils.AuthenticationUtils;
import com.cherrysoft.cryptocurrency.service.CryptoCoinService;
import com.cherrysoft.cryptocurrency.service.criteria.CryptoCoinFilterCriteria;
import com.cherrysoft.cryptocurrency.web.dtos.CryptoCoinDTO;
import com.cherrysoft.cryptocurrency.web.hateoas.CryptoCoinModelAssembler;
import com.cherrysoft.cryptocurrency.web.mapper.CryptoCoinMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
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
  private final CryptoCoinModelAssembler cryptoCoinModelAssembler;
  private final PagedResourcesAssembler<CryptoCoin> cryptoCoinPagedResourcesAssembler;

  @Operation(summary = "Get crypto coins")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = CryptoCoinDTO.class)))
  })
  @Parameter(name = "favorite", description = "Return user's favorite coins", schema = @Schema(type = "string"))
  @Parameter(name = "public", description = "Return all public coins", schema = @Schema(type = "string"))
  @Parameter(name = "owned", description = "Return owned coins", schema = @Schema(type = "string"))
  @Parameter(name = "all", description = "Return all coins", schema = @Schema(type = "string"))
  @GetMapping
  public PagedModel<CryptoCoinDTO> getCryptoCoins(
      @RequestParam @Parameter(hidden = true) Map<String, String> options,
      @ParameterObject
      @PageableDefault
      @SortDefault(sort = "currentPrice", direction = Sort.Direction.DESC)
      Pageable pageable
  ) {
    String username = authenticationUtils.getUsername();
    CryptoCoinFilterCriteria filterCriteria = new CryptoCoinFilterCriteria(options, pageable);
    Page<CryptoCoin> result = cryptoCoinService.getCryptoCoins(username, filterCriteria);
    return cryptoCoinPagedResourcesAssembler.toModel(result, cryptoCoinModelAssembler);
  }

  @Operation(summary = "Create crypto coin for logged user")
  @ApiResponse(responseCode = "201", description = "Crypto coin created", content = {
      @Content(schema = @Schema(implementation = CryptoCoinDTO.class))
  })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CryptoCoinDTO> saveCryptoCoin(
      @RequestBody @Valid CryptoCoinDTO cryptoCoinDto
  ) {
    String username = authenticationUtils.getUsername();
    CryptoCoin result = cryptoCoinService.addCryptoCoinTo(username, cryptoCoinMapper.toCryptoCoin(cryptoCoinDto));
    return ResponseEntity
        .created(URI.create(String.format("%s/%s", BASE_URL, result.getId())))
        .body(cryptoCoinModelAssembler.toModel(result));
  }

  @Operation(summary = "Mark the crypto coin with the provided ID as favorite")
  @ApiResponse(responseCode = "200", description = "OK", content = {
      @Content(schema = @Schema(implementation = FavoriteResult.class))
  })
  @PatchMapping("/favorite")
  public ResponseEntity<FavoriteResult> markAsFavorite(@RequestParam String coinId) {
    String username = authenticationUtils.getUsername();
    FavoriteResult result = cryptoCoinService.markAsFavorite(username, coinId);
    return ResponseEntity.ok(result);
  }

}
