package com.example.dummyjson.client;

import com.example.dummyjson.dto.Product;
import com.example.dummyjson.dto.ProductResponse;
import com.example.dummyjson.exception.BadRequestException;
import com.example.dummyjson.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient.Builder builder,
                         @Value("${dummyjson.api.url}") String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public List<Product> getAllProducts() {
        return webClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .map(ProductResponse::getProducts)
                .block();
    }

    public Product getProductById(Long id) {
        return webClient.get()
                .uri("/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode().value() == HttpStatus.NOT_FOUND.value()) {
                        throw new NotFoundException("Product not found for id: " + id);
                    }
                    throw new BadRequestException("Client error: " + response.statusCode() + " - " + response);
                })
                .bodyToMono(Product.class)
                .block();
    }
}
