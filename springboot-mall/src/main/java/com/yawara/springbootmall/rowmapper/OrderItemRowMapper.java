package com.yawara.springbootmall.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import com.yawara.springbootmall.model.OrderItem;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {


    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setAmount(rs.getInt("amount"));

        //不在OrderItem類別中的屬性，而是在product類別中的屬性
        //擴充OrderItem
        orderItem.setProduct_name(rs.getString("product_name"));
        orderItem.setImage_url(rs.getString("image_url"));

        return orderItem;
    }
}
