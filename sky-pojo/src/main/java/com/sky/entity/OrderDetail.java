package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Order details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //Name
    private String name;

    //Order id
    private Long orderId;

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
}
