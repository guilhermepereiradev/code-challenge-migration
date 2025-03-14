package com.example.dummyjson.dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class ProductResponseTest {

    @Test
    public void testGetAndSetter(){
        Long expectTotal = 10L;
        Long expectSkip = 2L;
        Long expectLimit = 30L;
        Long expectSize = 1L;

        Product expectProduct = new Product();
        expectProduct.setId(1L);
        expectProduct.setTitle("A dummy title");
        expectProduct.setDescription("A dummy description");
        expectProduct.setPrice(Double.valueOf("2.1"));

        ProductResponse productResponse = new ProductResponse();
        productResponse.setTotal(10L);
        productResponse.setSkip(2L);
        productResponse.setLimit(30L);
        productResponse.setProducts(List.of(expectProduct));

        assertEquals(expectTotal, productResponse.getTotal());
        assertEquals(expectSkip, productResponse.getSkip());
        assertEquals(expectLimit, productResponse.getLimit());
        assertEquals(expectSize, productResponse.getProducts().size());
    }
}
