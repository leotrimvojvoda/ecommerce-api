package com.vojvoda.ecomerceapi.core.user;

import com.vojvoda.ecomerceapi.core.user.dto.request.CreateUser;
import com.vojvoda.ecomerceapi.core.user.dto.response.ViewUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    private void createUser(@Valid CreateUser createUser){
        userService.createUser(createUser);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ViewUser> getUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    private ResponseEntity<ViewUser> getUserByEmail(@RequestParam(name = "email") String email){
        return ResponseEntity.ok(userService.findUserByEmail(email));
    }

    @GetMapping("/all")
    private ResponseEntity<List<ViewUser>> findAllUsers(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @DeleteMapping
    private ResponseEntity<?> deleteUser(@RequestParam Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }


}
