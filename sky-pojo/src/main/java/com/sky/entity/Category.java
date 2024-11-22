package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //Type: 1 dish  2 set meal
    private Integer type;

    //Category Name
    private String name;

    //Sort
    private Integer sort;

    //Category status 0 disabled, 1 enabled
    private Integer status;

    //Create time
    private LocalDateTime createTime;

    //Update time
    private LocalDateTime updateTime;

    //Create user
    private Long createUser;

    //Update user
    private Long updateUser;
}
