package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Client-side user login
 */
@Data
public class UserLoginDTO implements Serializable {

    private String code;

}
