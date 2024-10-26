package com.sky.dto;

import com.sky.entity.OrderDetail;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDTO implements Serializable {

    private Long id;

    //Order number
    private String number;

    //Order status: 1 pending payment, 2 pending delivery, 3 delivered, 4 completed, 5 canceled
    private Integer status;

    //Order user id
    private Long userId;

    //Address id
    private Long addressBookId;

    //Order time
    private LocalDateTime orderTime;

    //Checkout time
    private LocalDateTime checkoutTime;

    //Payment method: 1 WeChat, 2 Alipay
    private Integer payMethod;

    //Amount actually received
    private BigDecimal amount;

    //Remark
    private String remark;

    //Username
    private String userName;

    //Phone number
    private String phone;

    //Address
    private String address;

    //Receiver
    private String consignee;

    private List<OrderDetail> orderDetails;

}
