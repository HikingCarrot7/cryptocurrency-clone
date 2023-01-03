package com.cherrysoft.cryptocurrency.web.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Relation(itemRelation = "user", collectionRelation = "users")
public class CryptoUserDTO extends RepresentationModel<CryptoUserDTO> {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(message = "Enter username")
  @Size(min = 2, max = 20, message = "Username should be between {min} and {max} characters")
  private final String username;

  @NotBlank(message = "Enter password")
  @Size(min = 8, max = 20, message = "Password should be between {min} and {max} characters")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Getter(AccessLevel.NONE)
  private final String password;

  @JsonIgnore
  public String getPassword() {
    return password;
  }

}
