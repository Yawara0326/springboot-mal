package com.yawara.springbootmall.service;

import com.yawara.springbootmall.dto.createOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, createOrderRequest createOrderRequest);

}
