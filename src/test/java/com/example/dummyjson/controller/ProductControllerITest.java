package com.example.dummyjson.controller;

import com.example.dummyjson.client.ProductClient;
import com.example.dummyjson.dto.Product;
import com.example.dummyjson.exception.BadRequestException;
import com.example.dummyjson.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductClient productClient;

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = List.of(
                new Product(1L, "Product One", "Description", Double.valueOf("9.99")),
                new Product(2L, "Product Two", "Description", Double.valueOf("9.99"))
        );
        when(productClient.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Essence Mascara Lash Princess");
        product.setDescription("The Essence Mascara Lash Princess is a popular mascara known for its volumizing and " +
                "lengthening effects. Achieve dramatic lashes with this long-lasting and cruelty-free formula.");
        product.setPrice(9.99);

        when(productClient.getProductById(1L)).thenReturn(product);

        mockMvc.perform(get("/api/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(product.getId().intValue())))
                .andExpect(jsonPath("$.title", is(product.getTitle())))
                .andExpect(jsonPath("$.description", is(product.getDescription())))
                .andExpect(jsonPath("$.price", is(product.getPrice())));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productClient.getProductById(1L)).thenThrow(new NotFoundException(""));

        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(NotFoundException.class, result.getResolvedException()));
    }

    @Test
    public void testGetProductById_BadRequest() throws Exception {
        when(productClient.getProductById(1L)).thenThrow(new BadRequestException(""));

        mockMvc.perform(get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(BadRequestException.class, result.getResolvedException()));
    }
}
