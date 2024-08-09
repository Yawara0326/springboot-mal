package com.yawara.springbootmall.service;

import com.yawara.springbootmall.dto.ProductRequest;
import com.yawara.springbootmall.model.Product;

public interface ProductService {
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
