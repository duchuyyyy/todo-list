package com.todolist.todolist.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> saveUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.loadFromDto(userDto);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
