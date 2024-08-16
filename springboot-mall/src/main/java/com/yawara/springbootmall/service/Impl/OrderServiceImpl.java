package com.yawara.springbootmall.service.Impl;

import com.yawara.springbootmall.dao.OrderDao;
import com.yawara.springbootmall.dao.ProductDao;
import com.yawara.springbootmall.dao.UserDao;
import com.yawara.springbootmall.dto.BuyItem;
import com.yawara.springbootmall.dto.OrderQueryParams;
import com.yawara.springbootmall.dto.createOrderRequest;
import com.yawara.springbootmall.model.Order;
import com.yawara.springbootmall.model.OrderItem;
import com.yawara.springbootmall.model.Product;
import com.yawara.springbootmall.model.User;
import com.yawara.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        return orderDao.countOrders(orderQueryParams);
    }

    //涉及修改兩張以上的資料表，加上Transactional，確保若遇到中斷情形，會rollback資料庫操作
    @Transactional
    @Override
    public Integer createOrder(Integer userId, createOrderRequest createOrderRequest) {

        //檢查user存不存在
        User user = userDao.getUserById(userId);

        if (user == null){
            log.warn("用戶 {} 不存在",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItemList = new ArrayList<>();

        //計算訂單總花費資訊
        int totalAmount = 0;

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){

            Product product = productDao.getProductById(buyItem.getProductId());

            //檢查商品存不存在與商品數量是否足夠(大於庫存數量)
            if (product == null){
                log.warn("商品 {} 不存在",buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if (product.getStock() < buyItem.getQuantity()){
                log.warn("商品 {} 數量不足，庫存數量 {} ，欲購買數量{} ",
                        product.getProductId(),product.getStock(),buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());

            int amount = buyItem.getQuantity()* product.getPrice();
            totalAmount += amount;

            //轉換BuyItem to OrderItem 以紀錄訂單詳細資訊
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);

        }

        //創建訂單總資訊
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        //創建訂單詳細資訊
        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        //取得商品摘要資訊
        Order order = orderDao.getOrderById(orderId);

        //取得商品詳細資訊
        List<OrderItem> orderItemList = orderDao.getOrderItemsById(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for(Order order : orderList){
            List<OrderItem> orderItemList = orderDao.getOrderItemsById(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }


}
