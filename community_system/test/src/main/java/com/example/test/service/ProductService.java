package com.example.test.service;

import com.example.test.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getProductList(String productName,int page,int limit);

    public Product getProductByName(String productName);

    public Product getProductById(String productId);

    public int addProduct(Product product);

    public int updateProduct(Product product);

    public int deleteProduct(String productId);

    public int getProductCount();

}
