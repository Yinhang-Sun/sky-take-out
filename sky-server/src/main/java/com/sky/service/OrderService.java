package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
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

    /**
     * One more order
     * @param id
     * @return
     */
    void repetition(Long id);

    /**
     * Conditional search order
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * Order quantity statistics for each status
     * @return
     */
    OrderStatisticsVO statistics();

    /**
     * Accept order
     * @param ordersConfirmDTO
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * Reject order
     * @param ordersRejectionDTO
     * @return
     * @throws Exception
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * Cancel order
     * @param ordersCancelDTO
     */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * Delivery order
     * @param id
     * @return
     */
    void delivery(Long id);

    /**
     * Complete order
     * @param id
     * @return
     */
    void complete(Long id);

}
