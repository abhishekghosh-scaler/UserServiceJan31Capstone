package com.scaler.userservicejan31capstone.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.userservicejan31capstone.models.Role;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority
{
    public CustomGrantedAuthority(){}

    String authority;
    public CustomGrantedAuthority(Role role)
    {
        this.authority = role.getValue();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
