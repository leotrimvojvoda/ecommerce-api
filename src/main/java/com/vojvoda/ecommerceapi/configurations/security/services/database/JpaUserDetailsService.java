package com.vojvoda.ecommerceapi.configurations.security.services.database;


import com.vojvoda.ecommerceapi.configurations.security.user.SecurityUser;
import com.vojvoda.ecommerceapi.core.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    var u = userRepository.findByEmail(username);

    return u.map(SecurityUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
  }
}