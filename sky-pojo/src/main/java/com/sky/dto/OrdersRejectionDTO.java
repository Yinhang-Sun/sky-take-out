package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersRejectionDTO implements Serializable {

    private Long id;

    //Reason for order rejection
    private String rejectionReason;

}
