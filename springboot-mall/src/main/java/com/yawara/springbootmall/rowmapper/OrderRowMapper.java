package com.yawara.springbootmall.rowmapper;

import com.yawara.springbootmall.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {


    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotal_amount(rs.getInt("total_amount"));
        order.setCreate_date(rs.getDate("create_date"));
        order.setLast_modified_date(rs.getDate("last_modified_date"));

        return order;
    }
}