package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.controller.admin.CommonController;
import com.sky.dto.*;
import com.sky.entity.*;
import com.sky.exception.AddressBookBusinessException;
import com.sky.exception.OrderBusinessException;
import com.sky.exception.ShoppingCartBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrderService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommonController commonController;


    /**
     * User order
     * @param ordersSubmitDTO
     * @return
     */
    @Transactional
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {

        //1. Handle exceptions,
        // empty address book
        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        //Query the shopping cart data of the current user
        Long userId = BaseContext.getCurrentId();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.list(shoppingCart);
        // empty shopping cart
        if (shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        //2. Insert 1 data into order table
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setNumber(String.valueOf(System.currentTimeMillis()));
        orders.setPhone(addressBook.getPhone());
        orders.setConsignee(addressBook.getConsignee());
        orders.setUserId(userId);

        orderMapper.insert(orders);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        //3. Insert many data into order_detail
        for (ShoppingCart cart : shoppingCartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());//set order id related to the current order detail
            orderDetailList.add(orderDetail);
        }

        orderDetailMapper.insertBatch(orderDetailList);

        //4. Clean shopping cart data of the current user
        shoppingCartMapper.deleteByUserId(userId);

        //5. Encapsulate VO return result
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderTime(orders.getOrderTime())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .build();

        return orderSubmitVO;
    }

    /**
     * Order payment
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        //Currently logged-in user id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        //Call the WeChat payment interface to generate a prepayment transaction order
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(), //Merchant order number
                new BigDecimal(0.01), //Payment amount, unit: yuan
                "Sky-Take-Out Order", //Product description
                user.getOpenid() //WeChat-user’s openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("The order has been paid");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    /**
     * Payment successful, order status modified
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {
        //Currently logged-in user id
        Long userId = BaseContext.getCurrentId();

        // Query the current user's order based on the order number
        Orders ordersDB = orderMapper.getByNumberAndUserId(outTradeNo, userId);

        //Update the status, payment method, payment status, and checkout time of the order based on the order id
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }

    /**
     * user orders pagination query
     * @param pageNum
     * @param pageSize
     * @param status Order status: 1 Pending payment 2 Waiting for order 3 Order received 4 Delivery 5 Completed 6 Canceled
     * @return
     */
    public PageResult pageQueryForUser(int pageNum, int pageSize, Integer status) {
        //set pagination
        PageHelper.startPage(pageNum, pageSize);

        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());
        ordersPageQueryDTO.setStatus(status);

        //pagination conditional query
        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);

        List<OrderVO> list = new ArrayList<>();

        //Query the order details and encapsulate them into OrderVO for response
        if(page != null && page.getTotal() > 0){
            for (Orders orders : page) {
                Long orderId = orders.getId();//order id

                //query order details
                List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetails);

                list.add(orderVO);
            }
        }
        return new PageResult(page.getTotal(), list);
    }

    /**
     * Query order details
     * @param id
     * @return
     */
    public OrderVO details(Long id) {
        //query order by id
        Orders orders = orderMapper.getById(id);

        //query the dish / setmeal detail related to the orders
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        //Encapsulate orders and orderDetailList into an OrderVO and return it
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(orders, orderVO);
        orderVO.setOrderDetailList(orderDetailList);

        return orderVO;
    }

    /**
     * Cancel order
     * @param id
     * @return
     */
    public void userCancelById(Long id) throws Exception {
        //Query order by id
        Orders ordersDB = orderMapper.getById(id);

        //Check if the order exist or not
        if(ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }

        //Order status: 1 Pending payment 2 Waiting for order 3 Order received 4 Delivery 5 Completed 6 Canceled
        if(ordersDB.getStatus() > 2) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersDB.getId());

        //If the order is canceled while the order is pending, a refund is required.
        if(ordersDB.getStatus().equals(orders.TO_BE_CONFIRMED)) {
            //Call WeChat payment refund interface
            weChatPayUtil.refund(
                    ordersDB.getNumber(), //Merchant order number
                    ordersDB.getNumber(), //Merchant refund order number
                    new BigDecimal(0.01),//refund amount, unit: dollar
                    new BigDecimal(0.01));//Original order amount

            //Change payment status to refund
            orders.setPayStatus(Orders.REFUND);
        }

        //Update order status, cancellation reason, cancellation time
        orders.setStatus(orders.CANCELLED);
        orders.setCancelReason("User Cancel Order");
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * One more order
     * @param id
     * @return
     */
    public void repetition(Long id) {
        //Query the current user
        Long userId = BaseContext.getCurrentId();

        //Query the current order detail by order id
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);

        //Convert the order detail object into shopping cart object
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map(x -> {
            ShoppingCart shoppingCart = new ShoppingCart();

            //Re-copy the dish information in the original order details to the shopping cart object
            BeanUtils.copyProperties(x, shoppingCart, "id");
            shoppingCart.setUserId(userId);
            shoppingCart.setCreateTime(LocalDateTime.now());

            return shoppingCart;
        }).collect(Collectors.toList());

        //Insert the shopping cart object into database in batches
        shoppingCartMapper.insertBatch(shoppingCartList);
    }

    /**
     * Order search
     * @param ordersPageQueryDTO
     * @return
     */
    public PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());

        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);

        //For some order status, additional order dish information needs to be returned
        // and Orders converted to OrderVO.
        List<OrderVO> orderVOList = getOrderVOList(page);

        return new PageResult(page.getTotal(), orderVOList);
    }


    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        //Need to return order dish information and customize the OrderVO response result
        List<OrderVO> orderVOList = new ArrayList<>();

        List<Orders> ordersList = page.getResult();
        if(!CollectionUtils.isEmpty(ordersList)) {
            for (Orders orders : ordersList) {
                //copy common fields into OrderVO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                String orderDishes = getOrderDishesStr(orders);

                //Encapsulate order dish information into orderVO and add it to orderVOList
                orderVO.setOrderDishes(orderDishes);
                orderVOList.add(orderVO);
            }
        }
        return orderVOList;
    }

    /**
     * Get the dish information string based on the order id
     * @param orders
     * @return
     */
    private String getOrderDishesStr(Orders orders) {
        // Query order dish details (dishes and quantity in order)
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(orders.getId());

        // Concatenate each order dish information into a string (format: Kung Pao Chicken*3;)
        List<String> orderDishList = orderDetailList.stream().map(x -> {
            String orderDish = x.getName() + "*" + x.getNumber() + ";";
            return orderDish;
        }).collect(Collectors.toList());

        // Splice together all the dish information corresponding to the order
        return String.join("", orderDishList);

    }

    /**
     * Order quantity statistics for each status
     * @return
     */
    public OrderStatisticsVO statistics() {
        //According to the status, query the number of orders to be received,
        // to be delivered, and in delivery.
        Integer toBeConfirmed = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        Integer confirmed = orderMapper.countStatus(Orders.CONFIRMED);
        Integer deliveryInProgress = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);

        //Encapsulate the queried data into orderStatisticsVO to respond
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);
        return orderStatisticsVO;
    }

    /**
     * Accept order
     * @param ordersConfirmDTO
     */
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        Orders orders = Orders.builder()
                .id(ordersConfirmDTO.getId())
                .status(Orders.CONFIRMED)
                .build();

        orderMapper.update(orders);
    }

    /**
     * Reject order
     * @param ordersRejectionDTO
     * @return
     * @throws Exception
     */
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        //Query order by id
        Orders ordersDB = orderMapper.getById(ordersRejectionDTO.getId());

        //Only when the order exist and status is 2(TO_BE_CONFIRMED), you can reject it
        if (ordersDB == null || ordersDB.getStatus().equals(Orders.TO_BE_CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        //payment status
        Integer payStatus = ordersDB.getPayStatus();
        if (payStatus == Orders.PAID) {
            //user has paid, and need refund
            String refund = weChatPayUtil.refund(
                    ordersDB.getNumber(),
                    ordersDB.getNumber(),
                    new BigDecimal(0.01),
                    new BigDecimal(0.01));
            log.info("Refund：{}", refund);
        }

        //Rejection of the order requires a refund, and the order status,
        // order rejection reason, and cancellation time are updated based on the order ID.
        Orders orders = new Orders();
        orders.setId(ordersDB.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        orders.setCancelTime(LocalDateTime.now());

        orderMapper.update(orders);
    }

    /**
     * Cancel order
     * @param ordersCancelDTO
     */
    public void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception {
        //Query order by id
        Orders ordersDB = orderMapper.getById(ordersCancelDTO.getId());

        //payment status
        Integer payStatus = ordersDB.getPayStatus();
        if (payStatus == Orders.PAID) {
            //user paid, need to refund
            String refund = weChatPayUtil.refund(
                    ordersDB.getNumber(),
                    ordersDB.getNumber(),
                    new BigDecimal(0.01),
                    new BigDecimal(0.01));
            log.info("Refund: {}", refund);
        }

        //If the admin side cancels the order, a refund is required.
        // The order status, cancellation reason,
        // and cancellation time are updated according to the order ID.
        Orders orders = new Orders();
        orders.setId(ordersCancelDTO.getId());
        orders.setStatus(Orders.CANCELLED);
        orders.setCancelReason(ordersCancelDTO.getCancelReason());
        orders.setCancelTime(LocalDateTime.now());
        orderMapper.update(orders);
    }

    /**
     * Delivery order
     * @param id
     * @return
     */
    public void delivery(Long id) {
        //Query order by id
        Orders ordersDB = orderMapper.getById(id);

        //Check if the order exists or not, and the status is 3 or not
        if (ordersDB == null || !ordersDB.getStatus().equals(Orders.CONFIRMED)) {
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }

        Orders orders = new Orders();
        orders.setId(ordersDB.getId());

        //Update the order status, the status is converted to delivery in progress
        orders.setStatus(Orders.DELIVERY_IN_PROGRESS);

        orderMapper.update(orders);
    }
}
