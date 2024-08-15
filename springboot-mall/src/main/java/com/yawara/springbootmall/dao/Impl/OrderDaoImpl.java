package com.yawara.springbootmall.dao.Impl;

import com.mysql.cj.jdbc.MysqlParameterMetadata;
import com.yawara.springbootmall.dao.OrderDao;
import com.yawara.springbootmall.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Integer createOrder(Integer userId, Integer totalAmount) {
        //創建訂單的sql指令
        String sql = "INSERT INTO `order`(user_id, total_amount, create_date, last_modified_date) VALUES(:userId, :totalAmount, :createDate, :lastModifiedDate)";

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
    public void createOrerItems(Integer orderId, List<OrderItem> orderItemList) {
        //創建訂單資訊的sql
        String sql = "INSERT INTO `order_item`(order_id, product_id, quantity, amount) VALUE(:orderId, :productId, :quantity, :amount)";

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
}
