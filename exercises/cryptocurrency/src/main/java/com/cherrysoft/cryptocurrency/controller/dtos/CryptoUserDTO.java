package com.cherrysoft.cryptocurrency.controller.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
@Builder
public class CryptoUserDTO {
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private final Long id;

  @NotBlank(message = "Enter username")
  @Size(min = 2, max = 20, message = "Username should be between {min} and {max} characters")
  private final String username;

  @NotBlank(message = "Enter password")
  @Size(min = 8, max = 20, message = "Password should be between {min} and {max} characters")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private final String password;
}
