package com.yawara.springbootmall.dao;

import com.yawara.springbootmall.modell.Product;

public interface ProductDao {

    //根據productID回傳Product物件
    Product getProductById(Integer productId);

}
