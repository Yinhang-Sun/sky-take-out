package com.sky.vo;

import com.sky.entity.DishFlavor;
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
public class DishVO implements Serializable {

    private Long id;
    //Dish name
    private String name;
    //Dish category id
    private Long categoryId;
    //Dish price
    private BigDecimal price;
    //Dish image
    private String image;
    //Description
    private String description;
    //0 off-sale 1 on-sale
    private Integer status;
    //update time
    private LocalDateTime updateTime;
    //Category Name
    private String categoryName;
    //flavors associated with dishes
    private List<DishFlavor> flavors = new ArrayList<>();

    //private Integer copies;
}
