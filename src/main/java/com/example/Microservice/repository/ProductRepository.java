package com.example.Microservice.repository;

import com.example.Microservice.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Сохранение нового продукта
    public void saveProduct(Product product) {
        String sql = "INSERT INTO product (product, price) VALUES (?, ?)";
        jdbcTemplate.update(sql, product.getProduct(), product.getPrice());
    }

    // Получение всех продуктов
    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM product";
        RowMapper<Product> productRowMapper = (r, i) -> {
            Product rowObject = new Product();
            rowObject.setId(r.getInt("id"));
            rowObject.setProduct(r.getString("product"));
            rowObject.setPrice(r.getBigDecimal("price"));
            return rowObject;
        };
        return jdbcTemplate.query(sql, productRowMapper);
    }

    // Удаление продукта по ID
    public void deleteProductById(int id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Обновление цены продукта по ID
    public void updateProductPrice(int id, double newPrice) {
        String sql = "UPDATE product SET price = ? WHERE id = ?";
        jdbcTemplate.update(sql, newPrice, id);
    }

    // Поиск продуктов по названию
    // Фильтрация продуктов по названию и диапазону цен
    public List<Product> findProductsByName(String product, Double minPrice, Double maxPrice) {
        String sql = "SELECT * FROM product WHERE 1=1";

        List<Object> params = new ArrayList<>();

        if (product != null && !product.isEmpty()) {
            sql += " AND product LIKE ?";
            params.add("%" + product + "%");
        }

        if (minPrice != null) {
            sql += " AND price >= ?";
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql += " AND price <= ?";
            params.add(maxPrice);
        }

        RowMapper<Product> productRowMapper = (r, i) -> {
            Product rowObject = new Product();
            rowObject.setId(r.getInt("id"));
            rowObject.setProduct(r.getString("product"));
            rowObject.setPrice(r.getBigDecimal("price"));
            return rowObject;
        };

        return jdbcTemplate.query(sql, params.toArray(), productRowMapper);
    }
}
