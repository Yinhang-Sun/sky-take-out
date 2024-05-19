package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    /**
     * pagination query and sort in ordered time
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * Query order by id
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * Order quantity statistics for each status
     * @return
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);

    /**
     * Query order by order status and order time
     * @param status
     * @param orderTime
     * @return
     */
    // select * from orders where status = ? and order_time < (current_time - 15 min)
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);


    /**
     * Statistic turnover data by dynamical conditions
     * @param map
     * @return
     */
    Double sumByMap(Map map);

    /**
     * Statistic order number by dynamic conditions
     * @param map
     * @return
     */
    Integer countByMap(Map map);

    /**
     * Statistic sale top10 in a specific time range
     * @param begin
     * @param end
     * @return
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin,LocalDateTime end);

}
