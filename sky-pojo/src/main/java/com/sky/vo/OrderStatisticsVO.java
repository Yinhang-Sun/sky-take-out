package com.sky.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderStatisticsVO implements Serializable {
    //Quantity of orders to be received
    private Integer toBeConfirmed;

    //Quantity to be delivered
    private Integer confirmed;

    //Quantity in delivery
    private Integer deliveryInProgress;
}
