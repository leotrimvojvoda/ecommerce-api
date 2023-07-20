package com.vojvoda.ecommerceapi.configurations.security.user;

import com.vojvoda.ecommerceapi.configurations.security.authority.SecurityAuthority;
import com.vojvoda.ecommerceapi.core.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SecurityUser implements UserDetails {

  private final User user;

  public User getUser(){
    return user;
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities()
        .stream()
        .map(SecurityAuthority::new)
        .collect(Collectors.toList());
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

  public static User getCurrentUser(){
    return ((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
  }
}
