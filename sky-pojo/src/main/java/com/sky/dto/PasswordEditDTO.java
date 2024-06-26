package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordEditDTO implements Serializable {

    //employee id
    private Long empId;

    //Old Password
    private String oldPassword;

    //New Password
    private String newPassword;

}
