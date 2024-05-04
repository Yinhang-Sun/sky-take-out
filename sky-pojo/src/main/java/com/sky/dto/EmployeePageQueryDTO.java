package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    //employee's name
    private String name;

    //page number
    private int page;

    //Display the number of records per page
    private int pageSize;

}
