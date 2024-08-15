package com.yawara.springbootmall.model;

public class OrderItem {

    private Integer orderItmeId;
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Integer amount;

    public Integer getOrderItmeId() {
        return orderItmeId;
    }

    public void setOrderItmeId(Integer orderItmeId) {
        this.orderItmeId = orderItmeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
