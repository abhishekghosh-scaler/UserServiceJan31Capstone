package com.scaler.userservicejan31capstone.controllers;

import com.scaler.userservicejan31capstone.dtos.*;
import com.scaler.userservicejan31capstone.models.Token;
import com.scaler.userservicejan31capstone.models.User;
import com.scaler.userservicejan31capstone.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController
{
    UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto)
    {
        User user = userService.signup(signupRequestDto.getName(),
                signupRequestDto.getEmail(),
                signupRequestDto.getPassword());

        return UserDto.from(user);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto)
    {
        Token token = userService.login(loginRequestDto.getEmail(),
                loginRequestDto.getPassword());

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setTokenValue(token.getValue());
        return loginResponseDto;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto)
    {
        return null;
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable("token") String token)
    {
        User user = userService.validateToken(token);
        ResponseEntity<Boolean> responseEntity;

        if(user == null)
        {
            responseEntity = new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
        }
        else
        {
            responseEntity = new ResponseEntity<>(true, HttpStatus.OK);
        }

        return responseEntity;
    }
}
