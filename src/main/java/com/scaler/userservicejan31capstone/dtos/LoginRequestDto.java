package com.scaler.userservicejan31capstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto
{
    private String email;
    private String password;
}
