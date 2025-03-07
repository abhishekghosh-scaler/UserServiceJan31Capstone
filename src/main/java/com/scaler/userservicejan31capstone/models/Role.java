package com.scaler.userservicejan31capstone.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends Base
{
    private String value;
}
