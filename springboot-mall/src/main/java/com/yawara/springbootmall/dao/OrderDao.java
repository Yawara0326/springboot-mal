package com.yawara.springbootmall.dao;

import com.yawara.springbootmall.dto.OrderQueryParams;
import com.yawara.springbootmall.model.Order;
import com.yawara.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer countOrders(OrderQueryParams orderQueryParams);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    List<OrderItem> getOrderItemsById(Integer orderId);

}
