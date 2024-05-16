package com.sky.controller.user;

import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "C-side order API")
@Slf4j
public class OrderController {

    private OrderService orderService;

    /**
     * User order
     * @param ordersSubmitDTO
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("User order")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("User order, parameter:{}", ordersSubmitDTO);
        OrderSubmitVO  orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * Order Payment
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("Order Payment")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("Order payment: {}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("Generate prepayment transaction order: {}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }

    /**
     * Query history orders
     * @param page
     * @param pageSize
     * @param status Order status: 1 Pending payment 2 Waiting for order 3 Order received 4 Delivery 5 Completed 6 Canceled
     * @return
     */
    @GetMapping("/historyOrders")
    @ApiOperation("Query history orders")
    public Result<PageResult> page(int page, int pageSize, Integer status) {
        PageResult pageResult = orderService.pageQueryForUser(page, pageSize, status);
        return Result.success(pageResult);
    }

    /**
     * Query order details
     * @param id
     * @return
     */
    @GetMapping("/orderDetail/{id}")
    @ApiOperation("Query order details")
    public Result<OrderVO> details(@PathVariable("id") Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * Cancel order
     * @param id
     * @return
     */
    @PutMapping("/cancel/{id}")
    @ApiOperation("Cancel order")
    public Result cancel(@PathVariable("id") Long id) throws Exception {
        orderService.userCancelById(id);
        return Result.success();
    }

    /**
     * One more order
     * @param id
     * @return
     */
    @PostMapping("/repetition/{id}")
    @ApiOperation("One more order")
    public Result repetition(@PathVariable Long id) {
        orderService.repetition(id);
        return Result.success();
    }
}
