package com.yawara.springbootmall.controller;

import com.yawara.springbootmall.constant.ProductCategory;
import com.yawara.springbootmall.dto.ProductQueryParam;
import com.yawara.springbootmall.dto.ProductRequest;
import com.yawara.springbootmall.model.Product;
import com.yawara.springbootmall.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            //查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,
            //排序 Sorting
            @RequestParam(defaultValue = "created_date") String orderBy, //根據什麼欄位排序
            @RequestParam(defaultValue = "desc") String sort //升冪或降冪排序

    ){

        ProductQueryParam productQueryParam = new ProductQueryParam();
        productQueryParam.setCategory(category);
        productQueryParam.setSearch(search);
        productQueryParam.setOrderBy(orderBy);
        productQueryParam.setSort(sort);

        List<Product> productList= productService.getProducts(productQueryParam);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
         Product product = productService.getProductById(productId);

         if (product != null){
             return ResponseEntity.status(HttpStatus.OK).body(product);
         }else{
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId
                                                ,@RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductById(productId);

        //檢查product是否存在
        if (product != null){
            productService.updateProduct(productId, productRequest);
            Product updatedProduct = productService.getProductById(productId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProductById(productId);
        //不需要加檢查判斷，因為前端在意的是商品是否不存在資料庫中
        // 若本來就沒有在資料庫中，也符合前端想要的結果。
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
