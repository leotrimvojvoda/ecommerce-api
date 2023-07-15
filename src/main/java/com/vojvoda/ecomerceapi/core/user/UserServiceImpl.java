package com.vojvoda.ecomerceapi.core.user;

import com.vojvoda.ecomerceapi.configurations.security.authority.Authority;
import com.vojvoda.ecomerceapi.configurations.security.authority.AuthorityNotFoundException;
import com.vojvoda.ecomerceapi.configurations.security.authority.AuthorityRepository;
import com.vojvoda.ecomerceapi.core.user.dto.request.CreateUser;
import com.vojvoda.ecomerceapi.core.user.dto.request.UpdateUser;
import com.vojvoda.ecomerceapi.core.user.dto.response.ViewUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    @Override
    public User createUser(CreateUser createUser) {
        Authority authorities = authorityRepository.getAuthorityByName(createUser.getAuthority())
                .orElseThrow(() -> new AuthorityNotFoundException("Authority not found: " + createUser.getAuthority()));

        var user = new User();
        user.setFirstName(createUser.getFirstName());
        user.setLastName(createUser.getLastName());
        user.setEmail(createUser.getEmail());
        user.setPassword(passwordEncoder.encode(createUser.getPassword()));
        user.setAuthorities(Set.of(authorities));

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<ViewUser> findAllUsers() {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public void updateUser(UpdateUser updateUser) {

    }
}
