package com.cherrysoft.cryptocurrency.security;

import com.cherrysoft.cryptocurrency.model.CryptoUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SecurityCryptoUser implements UserDetails {
  private final CryptoUser cryptoUser;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(() -> "READ");
  }

  @Override
  public String getPassword() {
    return cryptoUser.getPassword();
  }

  @Override
  public String getUsername() {
    return cryptoUser.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
