package com.example.dummyjson.service;

import com.example.dummyjson.client.ProductClient;
import com.example.dummyjson.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductClient productClient;

    public List<Product> getAllProducts() {
        return productClient.getAllProducts();
    }

    public Product getProductById(Long id) {
        return productClient.getProductById(id);
    }
}
