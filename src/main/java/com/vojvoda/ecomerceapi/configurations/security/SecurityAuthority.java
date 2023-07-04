package com.vojvoda.ecomerceapi.configurations.security;


import com.vojvoda.ecomerceapi.configurations.security.authority.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

  private final Authority authority;

  @Override
  public String getAuthority() {
    return authority.getName();
  }
}
