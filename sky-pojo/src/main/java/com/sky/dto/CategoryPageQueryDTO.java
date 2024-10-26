package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryPageQueryDTO implements Serializable {

    //Page number
    private int page;

    //Number of records per page
    private int pageSize;

    //Category Name
    private String name;

    //Category type: 1 dish  2 set meal
    private Integer type;

}
