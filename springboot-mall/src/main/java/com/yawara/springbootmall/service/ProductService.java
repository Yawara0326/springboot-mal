package com.yawara.springbootmall.service;

import com.yawara.springbootmall.constant.ProductCategory;
import com.yawara.springbootmall.dto.ProductRequest;
import com.yawara.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(ProductCategory category, String search);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);

}
