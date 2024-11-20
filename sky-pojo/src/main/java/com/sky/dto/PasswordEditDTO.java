package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordEditDTO implements Serializable {

    //Employee id
    private Long empId;

    //Old Password
    private String oldPassword;

    //New Password
    private String newPassword;

}
