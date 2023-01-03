package com.cherrysoft.cryptocurrency.web.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginResponseDTO {
  private final String username;
  private final String accessToken;
}
