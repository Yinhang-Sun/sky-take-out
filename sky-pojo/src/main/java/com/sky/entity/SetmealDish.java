package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * setmeal dishes relation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //setmeal id
    private Long setmealId;

    //dish id
    private Long dishId;

    //Dish name (redundant field)
    private String name;

    //Original price of dishes
    private BigDecimal price;

    //Number of copies
    private Integer copies;
}
