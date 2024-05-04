package com.sky.vo;

import com.sky.entity.SetmealDish;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealVO implements Serializable {

    private Long id;

    //category id
    private Long categoryId;

    //setmeal name
    private String name;

    //setmeal price
    private BigDecimal price;

    //Status 0: Disabled 1: Enabled
    private Integer status;

    //Description
    private String description;

    //image
    private String image;

    //update time
    private LocalDateTime updateTime;

    //Category Name
    private String categoryName;

    //The relationship between setmeals and dishes
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}
