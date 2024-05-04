package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Order overview data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverViewVO implements Serializable {
    //Quantity of orders to be received
    private Integer waitingOrders;

    //Quantity to be delivered
    private Integer deliveredOrders;

    //Quantity completed
    private Integer completedOrders;

    //Cancelled quantity
    private Integer canceledOrders;

    //All orders
    private Integer allOrders;
}
