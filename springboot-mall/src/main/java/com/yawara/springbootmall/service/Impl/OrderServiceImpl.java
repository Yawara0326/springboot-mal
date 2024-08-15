package com.yawara.springbootmall.service.Impl;

import com.yawara.springbootmall.dao.OrderDao;
import com.yawara.springbootmall.dao.ProductDao;
import com.yawara.springbootmall.dto.BuyItem;
import com.yawara.springbootmall.dto.createOrderRequest;
import com.yawara.springbootmall.model.OrderItem;
import com.yawara.springbootmall.model.Product;
import com.yawara.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    //涉及修改兩張以上的資料表，加上Transactional，確保若遇到中斷情形，會rollback資料庫操作
    @Transactional
    @Override
    public Integer createOrder(Integer userId, createOrderRequest createOrderRequest) {

        List<OrderItem> orderItemList = new ArrayList<>();

        //計算訂單總花費資訊
        int totalAmount = 0;

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){

            Product product = productDao.getProductById(buyItem.getProductId());

            int amount = buyItem.getQuantity()* product.getPrice();
            totalAmount += amount;

            //轉換BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }


        //創建訂單總資訊
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        //創建訂單詳細資訊
        orderDao.createOrerItems(orderId,orderItemList);

        return orderId;
    }
}
