package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SetmealPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String name;

    //category id
    private Integer categoryId;

    //Status 0 means disabled, 1 means enabled
    private Integer status;

}
