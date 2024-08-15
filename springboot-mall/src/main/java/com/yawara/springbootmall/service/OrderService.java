package com.yawara.springbootmall.service;

import com.yawara.springbootmall.dto.createOrderRequest;
import com.yawara.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, createOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
}
