package com.yawara.springbootmall.dao.Impl;

import com.mysql.cj.jdbc.MysqlParameterMetadata;
import com.yawara.springbootmall.dao.OrderDao;
import com.yawara.springbootmall.dto.OrderQueryParams;
import com.yawara.springbootmall.model.Order;
import com.yawara.springbootmall.model.OrderItem;
import com.yawara.springbootmall.rowmapper.OrderItemRowMapper;
import com.yawara.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer countOrders(OrderQueryParams orderQueryParams) {
        String sql = "SELECT count(*) FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = addFilteringSql(sql, map, orderQueryParams);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return count;
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        //創建訂單的sql指令
        String sql = "INSERT INTO `order`(user_id, total_amount, create_date, last_modified_date) " +
                "VALUES(:userId, :totalAmount, :createDate, :lastModifiedDate)";

        //Map物件
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createDate", now);
        map.put("lastModifiedDate", now);

        //Keyholder
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //update(sql,new MapSqlParameterSource(map),kyholder)
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(map),keyHolder);

        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        //創建訂單資訊的sql
        String sql = "INSERT INTO `order_item`(order_id, product_id, quantity, amount) " +
                "VALUES (:orderId, :productId, :quantity, :amount)";

        //list[MapSqlParameterSorce]物件,長度 = orerItemList
        MapSqlParameterSource []  paramList = new MapSqlParameterSource[orderItemList.size()];

        //迴圈將orderItemList物件放入list map
        for (int i = 0; i < orderItemList.size(); i++){
            OrderItem orderItem = orderItemList.get(i);

            paramList[i] = new MapSqlParameterSource();
            paramList[i].addValue("orderId",orderId);
            paramList[i].addValue("productId",orderItem.getProductId());
            paramList[i].addValue("quantity",orderItem.getQuantity());
            paramList[i].addValue("amount",orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql,paramList);

    }

    @Override
    public Order getOrderById(Integer orderId) {

        //查詢商品資訊的sql指令
        String sql = "SELECT order_id, user_id, total_amount, create_date, last_modified_date " +
                "FROM `order` WHERE order_id = :orderId";

        //map物件
        Map<String, Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map, new OrderRowMapper());

        if (orderList.size()>0){
            return orderList.get(0);
        }else{
            return null;
        }

    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        String sql = "SELECT order_id, user_id, total_amount, create_date, last_modified_date FROM `order` WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sql = addFilteringSql(sql, map, orderQueryParams);

        //排序：寫死，讓前端無法更改訂單的排序紀錄
        sql = sql + " ORDER BY create_date DESC";

        //分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit",orderQueryParams.getLimit());
        map.put("offset",orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sql,map, new OrderRowMapper());

        return orderList;

    }

    @Override
    public List<OrderItem> getOrderItemsById(Integer orderId) {
        //查詢商品詳細資訊的sql指令：order_item left join product
        String sql = "SELECT oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url  " +
                "FROM `order_item` as oi " +
                "LEFT JOIN product as p " +
                "ON oi.product_id = p.product_id WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId",orderId);

        List<OrderItem> orderItemList= namedParameterJdbcTemplate.query(sql,map,new OrderItemRowMapper());


        return orderItemList;
    }

    public String addFilteringSql(String sql, Map<String, Object> map, OrderQueryParams orderQueryParams){
        if (orderQueryParams.getUserId() != null){
            sql = sql + " AND user_id = :userId";
            map.put("userId", orderQueryParams.getUserId());
        }
        return sql;
    }
}
