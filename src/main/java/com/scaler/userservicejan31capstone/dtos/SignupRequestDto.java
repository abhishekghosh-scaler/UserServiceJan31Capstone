package com.scaler.userservicejan31capstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto
{
    private String name;
    private String email;
    private String password;
}
