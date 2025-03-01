package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Address book
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    //User id
    private Long userId;

    //Receiver
    private String consignee;

    //Phone number
    private String phone;

    //Gender 0 female 1 male
    private String sex;

    //Provincial number
    private String provinceCode;

    //Provincial name
    private String provinceName;

    //City number
    private String cityCode;

    //City name
    private String cityName;

    //District level division number
    private String districtCode;

    //District level name
    private String districtName;

    //Address
    private String detail;

    //Label
    private String label;

    //Whether it is default 0 no 1 yes
    private Integer isDefault;
}
