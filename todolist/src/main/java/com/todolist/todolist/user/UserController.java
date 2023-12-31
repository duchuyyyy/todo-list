package com.todolist.todolist.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.todolist.dto.Request.RefreshTokenRequestDto;
import com.todolist.todolist.dto.Request.ResetPasswordRequestDto;
import com.todolist.todolist.dto.Response.ResetPasswordResponseDto;
import com.todolist.todolist.security.SecurityConstants;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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

    @GetMapping("/register/confirmtoken={confirmtoken}")
    public String verifiUser(@PathVariable String confirmtoken) {
        return userService.confirmToken(confirmtoken);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getInfoUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getEmailById(id), HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public void handleForgotPassword(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto) {
        User user = new User();
        user.loadFromResetPasswordRequestDto(resetPasswordRequestDto);
        userService.handleRequestResetPassword(user);
    }

    @PostMapping("/reset-password/resetpasswordtoken={resetpasswordtoken}")
    public void resetPassword(@PathVariable String resetpasswordtoken, @RequestBody ResetPasswordResponseDto resetPasswordResponseDto) {
        User user = new User();
        user.loadFromResetPasswordResponseDto(resetPasswordResponseDto);
        userService.handleResponseResetPassword(user, resetpasswordtoken);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshtoken = refreshTokenRequestDto.getRefreshtoken();
        User user = userService.getUserByRefreshtoken(refreshtoken);
        Boolean check = userService.validateRefreshToken(refreshtoken);
        if(check == true) {
            String newrefreshtoken =  userService.generateRefreshToken(user.getEmail());
            userService.setRefreshtoken(user.getEmail(), newrefreshtoken);
            return new ResponseEntity<>(userService.generateAccessToken(user.getEmail()) + "\n" + newrefreshtoken, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Refreshtoken is not valid!", HttpStatus.BAD_REQUEST);
        }
    }  

    @PostMapping("/logout")
    public ResponseEntity<HttpStatus> logout(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshtoken = refreshTokenRequestDto.getRefreshtoken().replace(SecurityConstants.BEARER, "");
        User user = userService.getUserByRefreshtoken(refreshtoken);
        userService.deleteRefreshtoken(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } 
}
