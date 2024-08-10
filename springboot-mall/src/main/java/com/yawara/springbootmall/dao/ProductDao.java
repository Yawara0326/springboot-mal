package com.yawara.springbootmall.dao;

import com.yawara.springbootmall.constant.ProductCategory;
import com.yawara.springbootmall.dto.ProductQueryParam;
import com.yawara.springbootmall.dto.ProductRequest;
import com.yawara.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProducts(ProductQueryParam productQueryParam);

    //根據productID回傳Product物件
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

}
