package com.vojvoda.ecommerceapi.core.user;

import com.vojvoda.ecommerceapi.configurations.exceptions.models.NotFoundException;
import com.vojvoda.ecommerceapi.configurations.security.authority.Authority;
import com.vojvoda.ecommerceapi.configurations.security.authority.AuthorityNotFoundException;
import com.vojvoda.ecommerceapi.configurations.security.authority.AuthorityRepository;
import com.vojvoda.ecommerceapi.core.user.dto.request.CreateUser;
import com.vojvoda.ecommerceapi.core.user.dto.request.UpdateUser;
import com.vojvoda.ecommerceapi.core.user.dto.response.ViewUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * The user service class is responsible to handle all the logic of interaction with the user entity.
 */
@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    /**
     * Creates a new user with the given details and saves it to the database.
     *
     * @param createUser details of the user to be created.
     * @return the created user
     */
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

    /**
     * Retrieves a user from the database by their unique identifier, if the user has not been deleted.
     *
     * @param id the unique identifier of the user to retrieve
     *
     * @return {@link ViewUser} dto that contains the necessary data for the user.
     */
    @Override
    public ViewUser findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found: "+id));
    }

    /**
     * Retrieves a user from the database by their email, if the user has not been deleted.
     *
     * @param email the unique identifier of the user to retrieve
     *
     * @return {@link ViewUser} dto that contains the necessary data for the user.
     * */
    @Override
    public ViewUser findUserByEmail(String email) {
        ViewUser viewUser = userRepository.findUserByEmail(email);

        if(viewUser != null)
            return viewUser;
        else throw new NotFoundException("User not found: "+email);
    }

    /**
     * Retrieves all users from the database
     * @return {@link List<ViewUser>} containing all users
     * */
    @Override
    public List<ViewUser> findAllUsers() {
        List<ViewUser> viewUser = userRepository.findAllUsers();

        if(viewUser != null)
            return viewUser;
        else throw new NotFoundException("No users found");
    }

    /**
     * Soft deletes user by id. The user does not get removed from the database, instead the user has a deleted_at
     * column that when we want to delete the user, we set it at the current time. If the row is empty, that means
     * the user is not deleted.
     * */
    @Transactional
    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }

    /**
     * Updates the necessary fields for a given user.
     *
     * @param updateUser, contains id to find the user and other fields that can be updated.
     * */
    @Override
    public void updateUser(UpdateUser updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElseThrow(() -> new NotFoundException("User not found: "+updateUser.getId()));

        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());

        userRepository.save(user);
    }
}
