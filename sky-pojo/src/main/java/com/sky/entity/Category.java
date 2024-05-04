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

    //sort
    private Integer sort;

    //Category status 0 disabled, 1 enabled
    private Integer status;

    //create time
    private LocalDateTime createTime;

    //update time
    private LocalDateTime updateTime;

    //create user
    private Long createUser;

    //update user
    private Long updateUser;
}
