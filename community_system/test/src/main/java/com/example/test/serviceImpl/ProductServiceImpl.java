package com.example.test.serviceImpl;

import com.example.test.entity.Product;
import com.example.test.mapper.ProductMapper;
import com.example.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    //将DAO注入Service层
    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> getProductList(String productName, int page, int limit) {
        return productMapper.getProductList(productName,page,limit);
    }

    @Override
    public Product getProductByName(String productName) {
        return productMapper.getProductByName(productName);
    }

    @Override
    public Product getProductById(String productId) {
        return productMapper.getProductById(productId);
    }

    @Override
    public int addProduct(Product product) {
        return productMapper.addProduct(product);
    }

    @Override
    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    @Override
    public int deleteProduct(String productId) {
        return productMapper.deleteProduct(productId);
    }

    @Override
    public int getProductCount() {
        return productMapper.getProductCount();
    }
}
