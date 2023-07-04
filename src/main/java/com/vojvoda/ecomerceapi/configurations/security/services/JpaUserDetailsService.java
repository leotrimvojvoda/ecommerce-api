package com.vojvoda.ecomerceapi.configurations.security.services;


import com.vojvoda.ecomerceapi.configurations.security.SecurityUser;
import com.vojvoda.ecomerceapi.core.user.UserRepository;
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
    var u = userRepository.findUserByUsername(username);

    return u.map(SecurityUser::new)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
  }
}
