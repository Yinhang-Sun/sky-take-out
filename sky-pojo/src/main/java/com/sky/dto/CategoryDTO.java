package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {

    //primary key
    private Long id;

    //Type 1 dish  2 setmeal
    private Integer type;

    //Category Name
    private String name;

    //Sort
    private Integer sort;

}
