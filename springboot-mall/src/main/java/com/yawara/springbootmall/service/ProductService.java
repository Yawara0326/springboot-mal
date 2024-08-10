package com.yawara.springbootmall.service;

import com.yawara.springbootmall.constant.ProductCategory;
import com.yawara.springbootmall.dto.ProductQueryParam;
import com.yawara.springbootmall.dto.ProductRequest;
import com.yawara.springbootmall.model.Product;

import java.util.List;

public interface ProductService {
    Integer countProduct (ProductQueryParam productQueryParam);
    List<Product> getProducts(ProductQueryParam productQueryParam);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);

}
