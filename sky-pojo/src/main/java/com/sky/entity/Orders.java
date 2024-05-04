package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * orders
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /**
     * Order status 1 Pending payment 2 Pending order 3 Order received 4 Delivery 5 Completed 6 Canceled
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    /**
     * Payment status 0 Unpaid 1 Paid 2 Refunded
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    //order number
    private String number;

    //Order status 1 Pending payment 2 Pending order 3 Order received 4 Delivery 5 Completed 6 Canceled 7 Refund
    private Integer status;

    //Order user id
    private Long userId;

    //address id
    private Long addressBookId;

    //order time
    private LocalDateTime orderTime;

    //Checkout time
    private LocalDateTime checkoutTime;

    //Payment method 1 WeChat, 2 Alipay
    private Integer payMethod;

    //Payment status 0 Unpaid 1 Paid 2 Refunded
    private Integer payStatus;

    //Amount actually received
    private BigDecimal amount;

    //Remark
    private String remark;

    //username
    private String userName;

    //Phone number
    private String phone;

    //address
    private String address;

    //Receiver
    private String consignee;

    //Reason for order cancellation
    private String cancelReason;

    //Reason for order rejection
    private String rejectionReason;

    //Order cancellation time
    private LocalDateTime cancelTime;

    //Estimated delivery time
    private LocalDateTime estimatedDeliveryTime;

    //Delivery status 1-Send immediately 0-Select a specific time
    private Integer deliveryStatus;

    //delivery time
    private LocalDateTime deliveryTime;

    //pack fee
    private int packAmount;

    //Quantity of tableware
    private int tablewareNumber;

    //Tableware quantity status 1 Provide according to meal size 0 Select specific quantity
    private Integer tablewareStatus;
}
