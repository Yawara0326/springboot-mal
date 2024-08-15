package com.yawara.springbootmall.dao;

import com.yawara.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrerItems(Integer orderId, List<OrderItem> orderItemList);


}
