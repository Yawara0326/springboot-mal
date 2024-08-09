package com.yawara.springbootmall.dao;

import com.yawara.springbootmall.dto.ProductRequest;
import com.yawara.springbootmall.model.Product;

public interface ProductDao {

    //根據productID回傳Product物件
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
