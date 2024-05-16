package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {


    /**
     * insert order data
     * @param orders
     */
    void insert(Orders orders);

    /**
     * Query orders based on order number and user id
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * Modify order information
     * @param orders
     */
    void update(Orders orders);

}
