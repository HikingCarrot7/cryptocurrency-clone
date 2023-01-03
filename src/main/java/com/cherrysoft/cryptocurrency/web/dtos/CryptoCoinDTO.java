package com.cherrysoft.cryptocurrency.web.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(itemRelation = "coin", collectionRelation = "coins")
public class CryptoCoinDTO extends RepresentationModel<CryptoCoinDTO> {
  private final String id;

  @NotNull
  private final String name;

  @NotNull
  private final String symbol;

  @NotNull
  private final String imageUrl;

  @NotNull
  private final BigDecimal currentPrice;

  @NotNull
  private final BigDecimal marketCapital;

  @NotNull
  private final BigDecimal totalVolume;

  @NotNull
  private final Integer marketCapitalRanking;

  @NotNull
  private final BigDecimal higherIn24Hours;

  @NotNull
  private final BigDecimal lowerIn24Hours;

  @Getter(AccessLevel.NONE)
  private final boolean isPublic;

  public boolean getIsPublic() {
    return isPublic;
  }

}
