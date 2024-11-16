package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Shopping cart
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //Name
    private String name;

    //User id
    private Long userId;

    //Dish id
    private Long dishId;

    //Setmeal id
    private Long setmealId;

    //Flavor
    private String dishFlavor;

    //Number
    private Integer number;

    //Amount
    private BigDecimal amount;

    //Image
    private String image;

    private LocalDateTime createTime;
}
