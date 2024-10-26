package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String name;

    //Category Id
    private Integer categoryId;

    //Status: 0 disabled, 1 enabled
    private Integer status;

}
