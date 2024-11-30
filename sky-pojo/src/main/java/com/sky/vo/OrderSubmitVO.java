package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubmitVO implements Serializable {
    //Order id
    private Long id;
    //Order number
    private String orderNumber;
    //Order amount
    private BigDecimal orderAmount;
    //Order time
    private LocalDateTime orderTime;
}
