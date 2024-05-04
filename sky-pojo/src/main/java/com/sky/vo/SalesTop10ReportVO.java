package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesTop10ReportVO implements Serializable {

    //List of product names, separated by commas, for example: Fish-flavored shredded pork,
    // Kung Pao chicken, boiled fish
    private String nameList;

    //Sales list, separated by commas, for example: 260,215,200
    private String numberList;

}
