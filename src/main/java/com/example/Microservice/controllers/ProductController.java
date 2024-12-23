package com.example.Microservice.controllers;

import com.example.Microservice.model.Product;
import com.example.Microservice.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public void saveProduct(@RequestBody Product product) {
        productRepository.saveProduct(product);
    }

    @GetMapping
    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productRepository.deleteProductById(id);
    }

    @PutMapping("/{id}/price")
    public void updateProductPrice(@PathVariable int id, @RequestBody UpdatePriceRequest request) {
        productRepository.updateProductPrice(id, request.getPrice());
    }


    @GetMapping("/search")
    public List<Product> filterProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {
        return productRepository.findProductsByName(name, minPrice, maxPrice);
    }

    public static class UpdatePriceRequest {
        private double price;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
