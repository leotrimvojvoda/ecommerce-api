package com.vojvoda.ecomerceapi.core.user;

import com.vojvoda.ecomerceapi.configurations.security.authority.Authority;
import com.vojvoda.ecomerceapi.configurations.security.authority.AuthorityNotFoundException;
import com.vojvoda.ecomerceapi.configurations.security.authority.AuthorityRepository;
import com.vojvoda.ecomerceapi.core.user.dto.request.CreateUser;
import com.vojvoda.ecomerceapi.core.user.dto.request.UpdateUser;
import com.vojvoda.ecomerceapi.core.user.dto.response.ViewUser;
import com.vojvoda.ecomerceapi.exceptions.models.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public ViewUser findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found: "+id));
    }

    @Override
    public ViewUser findUserByEmail(String email) {
        ViewUser viewUser = userRepository.findUserByEmail(email);

        if(viewUser != null)
            return viewUser;
        else throw new RuntimeException("User not found: "+email);
    }

    @Override
    public List<ViewUser> findAllUsers() {
        List<ViewUser> viewUser = userRepository.findAllUsers();

        if(viewUser != null)
            return viewUser;
        else throw new RuntimeException("No users found");
    }

    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public void updateUser(UpdateUser updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElseThrow(() -> new NotFoundException("User not found: "+updateUser.getId()));

        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());

        userRepository.save(user);
    }
}
