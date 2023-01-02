package com.cherrysoft.cryptocurrency.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthenticationUtils {

  public String getUsername() {
    return getAuthentication().getName();
  }

  @SuppressWarnings("unchecked")
  public Collection<GrantedAuthority> getAuthorities() {
    return (Collection<GrantedAuthority>) getAuthentication().getAuthorities();
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

}
