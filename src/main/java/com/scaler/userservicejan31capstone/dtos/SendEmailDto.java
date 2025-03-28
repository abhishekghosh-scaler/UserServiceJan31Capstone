package com.scaler.userservicejan31capstone.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto
{
    private String from;
    private String to;
    private String subject;
    private String body;
}
