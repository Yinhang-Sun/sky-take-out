package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersConfirmDTO implements Serializable {

    private Long id;
    //Order status: 1 Pending payment 2 Pending order 3 Order received 4 Delivery 5 Completed 6 Canceled 7 Refund
    private Integer status;

}
