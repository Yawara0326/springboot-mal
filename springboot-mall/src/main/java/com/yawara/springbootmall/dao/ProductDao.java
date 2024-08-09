package com.yawara.springbootmall.dao;

import com.yawara.springbootmall.model.Product;

public interface ProductDao {

    //根據productID回傳Product物件
    Product getProductById(Integer productId);

}
