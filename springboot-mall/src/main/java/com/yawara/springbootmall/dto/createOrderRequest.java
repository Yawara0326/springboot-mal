package com.yawara.springbootmall.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class createOrderRequest {

    @NotEmpty //用在List與Map，確認內容不可為空
    private List<BuyItem> buyItemList;

    public List<BuyItem> getBuyItemList() {
        return buyItemList;
    }

    public void setBuyItemList(List<BuyItem> buyItemList) {
        this.buyItemList = buyItemList;
    }
}
