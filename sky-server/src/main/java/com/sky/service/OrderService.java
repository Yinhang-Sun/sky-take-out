package com.sky.service;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;

public interface OrderService {


    /**
     * User order
     * @param ordersSubmitDTO
     * @return
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

    /**
     * Order payment
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * Payment successful, order status modified
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * Query history orders
     * @param page
     * @param pageSize
     * @param status Order status: 1 Pending payment 2 Waiting for order 3 Order received 4 Delivery 5 Completed 6 Canceled
     * @return
     */
    PageResult pageQueryForUser(int page, int pageSize, Integer status);

    /**
     * Query order details
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /**
     * Cancel order
     * @param id
     * @return
     */
    void userCancelById(Long id) throws Exception;

}
