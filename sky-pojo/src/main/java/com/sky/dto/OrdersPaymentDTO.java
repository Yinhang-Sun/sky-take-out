package com.sky.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrdersPaymentDTO implements Serializable {
    //Order number
    private String orderNumber;

    //Payment method
    private Integer payMethod;

}
