package com.vojvoda.ecommerceapi.core.user;

import com.vojvoda.ecommerceapi.core.user.dto.request.CreateUser;
import com.vojvoda.ecommerceapi.core.user.dto.request.UpdateUser;
import com.vojvoda.ecommerceapi.core.user.dto.response.ViewUser;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {

    User createUser(@Valid CreateUser createUser);

    ViewUser findUserById(Long id);

    ViewUser findUserByEmail(String email);

    List<ViewUser> findAllUsers();

    void deleteUserById(Long id);

    void updateUser(UpdateUser updateUser);

}