package com.yawara.springbootmall.service.Impl;

import com.yawara.springbootmall.dao.ProductDao;
import com.yawara.springbootmall.modell.Product;
import com.yawara.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {

        return productDao.getProductById(productId);

    }
}
