package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Setmeal overview
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetmealOverViewVO implements Serializable {
    //Quantity started for sale
    private Integer sold;

    //Quantity discontinued
    private Integer discontinued;
}
