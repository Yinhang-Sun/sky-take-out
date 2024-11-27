package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Setmeal dishes relation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //Setmeal id
    private Long setmealId;

    //Dish id
    private Long dishId;

    //Dish name (redundant field)
    private String name;

    //Original price of dishes
    private BigDecimal price;

    //Number of copies
    private Integer copies;
}
