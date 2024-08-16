package com.yawara.springbootmall.service;

import com.yawara.springbootmall.dto.OrderQueryParams;
import com.yawara.springbootmall.dto.CreateOrderRequest;
import com.yawara.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

}
