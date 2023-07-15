package com.vojvoda.ecomerceapi.core.user;

import com.vojvoda.ecomerceapi.core.user.dto.request.CreateUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    private void createUser(@Valid CreateUser createUser){
        userService.createUser(createUser);
    }


}
